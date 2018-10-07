package proj.bot.ticket.supports.types;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import api.proj.marble.lib.emoji.Emoji;
import api.proj.marble.tasks.threading.ThreadManager;
import api.proj.marble.tasks.threading.Threadder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.Ticket;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;
import proj.bot.ticket.utils.Messenger;

public class Ban implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Ban Appeals";
    }

    @Override
    public String getHelpMessage() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("To submit a ban appeal: " + "*\"" + Ticket.getInstance().prefix + "ban\"*");
        
        return sb.toString();
    }

    @Override
    public void enable(Guild guild) {
        getCategory(guild);
    }
    
    @Override
    public Category getCategory(Guild guild) {
        List<Category> cats = guild.getCategoriesByName(getCategoryName(guild), true);
        String name = getCategoryName(guild);

        Role pub = guild.getPublicRole();
        EnumSet<Permission> publicAllow = EnumSet.noneOf(Permission.class);
        EnumSet<Permission> publicDeny = EnumSet.of(Permission.MESSAGE_READ);
        
        Role support = SupportType.getSupportRole(guild);
        EnumSet<Permission> supportAllow = EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);
        EnumSet<Permission> supportDeny = EnumSet.noneOf(Permission.class);
        
        if(cats.isEmpty())
            return (Category) guild.getController().createCategory(name).addPermissionOverride(pub, publicAllow, publicDeny).addPermissionOverride(support, supportAllow, supportDeny).complete();
        else
            return cats.get(0);
    }

    @Override
    public String getCategoryName(Guild guild) {
        return Emoji.Hammer.getValue() + " Ban Appeals";
    }

    @Override
    public String createTicket(Guild guild, User user) {
        UUID uuid = UUID.randomUUID();

        Role pub = guild.getPublicRole();
        EnumSet<Permission> publicAllow = EnumSet.noneOf(Permission.class);
        EnumSet<Permission> publicDeny = EnumSet.of(Permission.MESSAGE_READ);

        Role support = SupportType.getSupportRole(guild);
        EnumSet<Permission> supportAllow = EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);
        EnumSet<Permission> supportDeny = EnumSet.noneOf(Permission.class);
        
        getCategory(guild)
                .createTextChannel(uuid.toString())
                .setTopic(user.getId())
                .addPermissionOverride(pub, publicAllow, publicDeny)
                .addPermissionOverride(support, supportAllow, supportDeny)
                .queue(ch -> {
                    addUserToTicket((TextChannel) ch, user);

                    EmbedBuilder embed = Messenger.getEmbedFrame(ch.getGuild());
                    embed.setDescription(Emoji.PageFacingUp.getValue() + " Ticket created! \nPlease explain the situation.");
                    Messenger.sendEmbed((TextChannel) ch, embed.build());
                });
        
        return uuid.toString();
    }

    @Override
    public List<TextChannel> getTickets(Guild guild, User user) {
        List<Channel> tickets = getCategory(guild).getChannels();
        List<TextChannel> ticket = new ArrayList<>();  
        tickets.stream().forEach(ch -> {
            if(ch instanceof TextChannel) {
                if(getOwner((TextChannel)ch).getId().equals(user.getId())) {
                    ticket.add((TextChannel)ch);
                }
            }
        });
        return ticket;
    }

    @Override
    public TextChannel getTicket(Guild guild, String channelName) {
        List<Channel> tickets = getCategory(guild).getChannels();
        List<TextChannel> ticket = new ArrayList<>();  
        tickets.stream().forEach(ch -> {
            if(ch instanceof TextChannel) {
                if(ch.getName().equals(channelName)) {
                    ticket.add((TextChannel)ch);
                }
            }
        });
        if (ticket.isEmpty())
            return null;
        else
            return ticket.get(0);
    }
    
    @Override
    public User getOwner(TextChannel ch) {
        return Ticket.getInstance().getJda().getUserById(ch.getTopic());
    }

    @Override
    public void closeTicket(TextChannel ch) {
        EmbedBuilder embed = Messenger.getEmbedFrame(ch.getGuild());
        embed.setDescription(Emoji.RedCircle.getValue() + " Ticket closing in 20 seconds.");
        Messenger.sendEmbed(ch, embed.build());
        UUID thread = ThreadManager.callNewThread(new Threadder() {
            @Override
            public void run() {
                ch.delete().queue();
            }
        }, 20000L);
        ThreadManager.get(thread).start();
    }

    @Override
    public void addUserToTicket(TextChannel ch, User user) {
        if(ch.getPermissionOverride(ch.getGuild().getMember(user)) == null) {
            EnumSet<Permission> supportAllow = EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE);
            EnumSet<Permission> supportDeny = EnumSet.noneOf(Permission.class);
            ch.createPermissionOverride(ch.getGuild().getMember(user)).setAllow(supportAllow).setDeny(supportDeny).queue();
        }
        EmbedBuilder embed = Messenger.getEmbedFrame(ch.getGuild());
        embed.setDescription(Emoji.GreenCheck.getValue() + " " + user.getName() + " added to ticket!");
        Messenger.sendEmbed((TextChannel) ch, embed.build());
    }

    @Override
    public void removeUserFromTicket(TextChannel ch, User user) {
        if (ch.getPermissionOverride(ch.getGuild().getMember(user)) != null) {
            ch.getPermissionOverride(ch.getGuild().getMember(user)).delete().queue();
        }

        EmbedBuilder embed = Messenger.getEmbedFrame(ch.getGuild());
        embed.setDescription(Emoji.CrossMark.getValue() + " " + user.getName() + " removed from ticket!");
        Messenger.sendEmbed((TextChannel) ch, embed.build());
    }

    @Override
    public void disable(Guild guild) {
        Category cat = getCategory(guild);
        for(Channel ch : cat.getChannels())
            ch.delete().queue();
        cat.delete().queue();
    }

}
