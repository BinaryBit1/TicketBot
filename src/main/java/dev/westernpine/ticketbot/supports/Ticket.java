package dev.westernpine.ticketbot.supports;

import java.util.EnumSet;
import java.util.UUID;

import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.sql.ServerTable;
import dev.westernpine.ticketbot.util.Messenger;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import proj.api.marble.lib.emoji.Emoji;
import proj.api.marble.lib.uid.UID;
import proj.api.marble.tasks.threading.ThreadManager;
import proj.api.marble.tasks.threading.Threadder;

public class Ticket {
    
    @Getter
    private SupportType type;

    @Getter
    private Guild guild;

    @Getter
    @Setter
    private UID id;

    private String owner;
    
    public Ticket(SupportType type, Guild guild, String owner, UID id) {
        this.type = type;
        this.guild = guild;
        this.id = id;
        this.owner = owner;
    }
    
    public Ticket(SupportType type, Guild guild, String owner) {
        this.type = type;
        this.guild = guild;
        generateId();
        while(type.containsChannel(guild, id))
            generateId();
        this.owner = owner;
    }
    
    public Ticket(SupportType type, Guild guild, UID id) {
        this.type = type;
        this.guild = guild;
        this.id = id;
    }
    
    public static Ticket from(TextChannel channel) {
        for(SupportType type : new ServerTable(channel.getGuild().getId()).getEnabledSupports()) {
            Category cat = type.getCategory(channel.getGuild());
            if(cat.getTextChannels().contains(channel))
                return new Ticket(type, channel.getGuild(), getOwner(channel), UID.from(channel.getName()));
        }
        return null;
    }
    
    public static Ticket from(Guild guild, UID id) {
        for(SupportType type : new ServerTable(guild.getId()).getEnabledSupports()) {
            TextChannel channel = new Ticket(type, guild, id).getChannel();
            if(channel != null) {
                return new Ticket(type, guild, getOwner(channel), id);
            }
        }
        return null;
    }
    
    private static String getOwner(TextChannel channel) {
        String owner = channel.getTopic();
        if(owner == null || owner == "")
            owner = TicketBot.getInstance().getJda().getSelfUser().getId();
        return owner;
    }
    
    public String getOwner() {
        if(owner == null || owner == "") {
            owner = TicketBot.getInstance().getJda().getSelfUser().getId();
            getChannel().getManager().setTopic(owner);
        }
        return owner;
    }
    
    public void create() {
        type.getCategory(guild)
        .createTextChannel(id.toString())
        .setTopic(owner)
        .addPermissionOverride(SupportType.getPublicRole(guild), SupportType.getPublicAllow(), SupportType.getPublicDeny())
        .addPermissionOverride(SupportType.getSupportRole(guild), SupportType.getSupportAllow(), SupportType.getSupportDeny())
        .addPermissionOverride(SupportType.getSelfMember(guild), SupportType.getSelfAllow(), SupportType.getSelfDeny())
        .queue(ch -> {
            EmbedBuilder embed = Messenger.getEmbedFrame(ch.getGuild());
            embed.setDescription(Emoji.PageFacingUp.getValue() + " **Ticket created!** \n\n" + type.getSupportType().getTicketCreatedMessage());
            Messenger.sendEmbed((TextChannel) ch, embed.build());
            addUser(TicketBot.getInstance().getJda().getUserById(owner));
        });
    }
    
    public TextChannel getChannel() {
        for (TextChannel ch : type.getCategory(guild).getTextChannels()) {
            if (UID.from(ch.getName()).equals(id)) {
                return (TextChannel) ch;
            }
        }
        return null;
    }
    
    public void addUser(User user) {
        TextChannel ch = getChannel();
        if(ch.getPermissionOverride(guild.getMember(user)) == null) {
            ch.createPermissionOverride(guild.getMember(user)).setAllow(SupportType.getSupportAllow()).setDeny(SupportType.getSupportDeny()).queue();
        }
        EmbedBuilder embed = Messenger.getEmbedFrame(guild);
        embed.setDescription(Emoji.GreenCheck.getValue() + " **" + user.getName() + "** added to ticket!");
        Messenger.sendEmbed(ch, embed.build());
        ch.sendMessage(user.getAsMention() + " Your ticket is ready.").queue(msg -> msg.delete().queue());
    }
    
    public void removeUser(User user) {
        TextChannel ch = getChannel();
        if (ch.getPermissionOverride(guild.getMember(user)) != null) {
            ch.getPermissionOverride(guild.getMember(user)).delete().queue();
        }

        EmbedBuilder embed = Messenger.getEmbedFrame(guild);
        embed.setDescription(Emoji.CrossMark.getValue() + " **" + user.getName() + "** removed from ticket!");
        Messenger.sendEmbed(ch, embed.build());
    }
    
    public void close() {
        TextChannel ch = getChannel();
        try {
            ch.getMemberPermissionOverrides().forEach(po -> {
                Member member = po.getMember();
                ch.putPermissionOverride(member).setAllow(EnumSet.of(Permission.MESSAGE_READ)).setDeny(EnumSet.of(Permission.MESSAGE_WRITE)).queue();
            });
        } catch(Exception e) {}
        
        EmbedBuilder embed = Messenger.getEmbedFrame(guild);
        embed.setDescription(Emoji.Lock.getValue() + " Ticket closing in `20` seconds.");
        Messenger.sendEmbed(ch, embed.build());
        UUID thread = ThreadManager.callNewThread(new Threadder() {
            @Override
            public void run() {
                ch.delete().queue();
            }
        }, 20000L);
        ThreadManager.get(thread).start();
    }
    
    private void generateId() {
        this.id = UID.from(type.getAbr() + "-" + UID.randomUID().toString().toLowerCase());
    }

}
