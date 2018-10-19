package proj.bot.ticket.supports;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import api.proj.marble.lib.emoji.Emoji;
import api.proj.marble.lib.uid.UID;
import api.proj.marble.tasks.threading.ThreadManager;
import api.proj.marble.tasks.threading.Threadder;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.utils.Messenger;

public class Ticket {
    
    @Getter
    private SupportType type;

    @Getter
    private Guild guild;

    @Getter
    @Setter
    private String id;

    private User owner;
    
    public Ticket(SupportType type, Guild guild, User owner, String id) {
        this.type = type;
        this.guild = guild;
        this.id = id;
        this.owner = owner;
    }
    
    public Ticket(SupportType type, Guild guild, User owner) {
        this.type = type;
        this.guild = guild;
        generateId();
        while(type.containsChannel(guild, id))
            generateId();
        this.owner = owner;
    }
    
    public Ticket(SupportType type, Guild guild, String id) {
        this.type = type;
        this.guild = guild;
        this.id = id;
    }
    
    public static Ticket from(TextChannel channel) {
        for(SupportType type : new ServerConfig(channel.getGuild().getId()).getEnabledSupports()) {
            return new Ticket(type, channel.getGuild(), getOwner(channel), channel.getName());
        }
        return null;
    }
    
    public static Ticket from(Guild guild, String id) {
        for(SupportType type : new ServerConfig(guild.getId()).getEnabledSupports()) {
            TextChannel channel = new Ticket(type, guild, id).getChannel();
            if(channel != null) {
                return new Ticket(type, guild, getOwner(channel), id);
            }
        }
        return null;
    }
    
    private static User getOwner(TextChannel channel) {
        User owner = TicketBot.getInstance().getJda().getUserById(channel.getTopic());
        if(owner == null)
            owner = TicketBot.getInstance().getJda().getSelfUser();
        return owner;
    }
    
    public User getOwner() {
        if(owner == null) {
            owner = TicketBot.getInstance().getJda().getSelfUser();
            getChannel().getManager().setTopic(owner.getId());
        }
        return owner;
    }
    
    public void create() {
        type.getCategory(guild)
        .createTextChannel(id)
        .setTopic(owner.getId())
        .addPermissionOverride(SupportType.getPublicRole(guild), SupportType.getPublicAllow(), SupportType.getPublicDeny())
        .addPermissionOverride(SupportType.getSupportRole(guild), SupportType.getSupportAllow(), SupportType.getSupportDeny())
        .queue(ch -> {
            EmbedBuilder embed = Messenger.getEmbedFrame(ch.getGuild());
            embed.setDescription(Emoji.PageFacingUp.getValue() + " **Ticket created!** \n\n" + type.getSupportType().getTicketCreatedMessage());
            Messenger.sendEmbed((TextChannel) ch, embed.build());
            addUser(owner);
        });
    }
    
    public TextChannel getChannel() {
        List<Channel> tickets = type.getCategory(guild).getChannels();
        List<TextChannel> ticket = new ArrayList<>();  
        tickets.stream().forEach(ch -> {
            if(ch instanceof TextChannel) {
                if(ch.getName().equals(id)) {
                    ticket.add((TextChannel)ch);
                }
            }
        });
        if (ticket.isEmpty())
            return null;
        else
            return ticket.get(0);
    }
    
    public void addUser(User user) {
        TextChannel ch = getChannel();
        if(ch.getPermissionOverride(guild.getMember(user)) == null) {
            ch.createPermissionOverride(guild.getMember(user)).setAllow(SupportType.getSupportAllow()).setDeny(SupportType.getSupportDeny()).queue();
        }
        EmbedBuilder embed = Messenger.getEmbedFrame(guild);
        embed.setDescription(Emoji.GreenCheck.getValue() + " **" + user.getName() + "** added to ticket!");
        Messenger.sendEmbed(ch, embed.build());
        ch.sendMessage(user.getAsMention()).queue(msg -> msg.delete().queue());
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
        
        ch.getMemberPermissionOverrides().forEach(po -> {
            Member member = po.getMember();
            ch.putPermissionOverride(member).setAllow(EnumSet.of(Permission.MESSAGE_READ)).setDeny(EnumSet.of(Permission.MESSAGE_WRITE)).queue();
        });
        
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
        this.id = type.getAbr() + "-" + UID.randomUID().toString().toLowerCase();
    }

}
