package proj.bot.ticket.supports;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import lombok.Getter;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.supports.types.Ban;
import proj.bot.ticket.supports.types.Billing;
import proj.bot.ticket.supports.types.Bug;
import proj.bot.ticket.supports.types.Question;
import proj.bot.ticket.supports.types.Rank;
import proj.bot.ticket.supports.types.Suggest;
import proj.bot.ticket.supports.types.Support;

public enum SupportType {

    BAN("ban", new Ban()),
    BILLING("billing", new Billing()),
    BUG("bug", new Bug()),
    QUESTION("question", new Question()),
    RANK("rank", new Rank()),
    ROLE("role", new proj.bot.ticket.supports.types.Role()),
    SUGGEST("suggest", new Suggest()),
    SUPPORT("support", new Support()),
    TICKET("ticket", new proj.bot.ticket.supports.types.Ticket()),
    ;
    
    @Getter
    private String string;
    
    @Getter
    private ISupportType supportType;
    
    SupportType(String string, ISupportType supportType){
        this.string = string;
        this.supportType = supportType;
    }
    
    public static SupportType fromString(String string) {
        for(SupportType type : SupportType.values()) {
            if(type.getString().equalsIgnoreCase(string)) {
                return type;
            }
        }
        return null;
    }
    
    public static Role getPublicRole(Guild guild) { return guild.getPublicRole(); }
    public static EnumSet<Permission> getPublicAllow() { return EnumSet.noneOf(Permission.class); }
    public static EnumSet<Permission> getPublicDeny() { return EnumSet.of(Permission.MESSAGE_READ); }

    public static Role getSupportRole(Guild guild) {
        return (guild.getRolesByName("Support Specialist", false).isEmpty()
                ? guild.getController().createRole().setName("Support Specialist").complete()
                : guild.getRolesByName("Support Specialist", false).get(0));
    }
    public static EnumSet<Permission> getSupportAllow() { return EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE); }
    public static EnumSet<Permission> getSupportDeny() { return EnumSet.noneOf(Permission.class); }
    
    public void enable(Guild guild) {
        getCategory(guild);
    }
    
    public Category getCategory(Guild guild) {
        List<Category> cats = guild.getCategoriesByName(supportType.getCategoryName(), true);
        String name = supportType.getCategoryName();
        if (cats.isEmpty())
            return (Category) guild.getController().createCategory(name)
                    .addPermissionOverride(getPublicRole(guild), getPublicAllow(), getPublicDeny())
                    .addPermissionOverride(getSupportRole(guild), getSupportAllow(), getSupportDeny()).complete();
        else
            return cats.get(0);
    }

    public List<Ticket> getTickets(Guild guild, User user) {
        List<Channel> channels = getCategory(guild).getChannels();
        List<Ticket> tickets = new ArrayList<>();  
        channels.stream().forEach(channel -> {
            if(channel instanceof TextChannel) {
                Ticket ticket = Ticket.from((TextChannel)channel);
                if(ticket != null) {
                    tickets.add(ticket);
                }
            }
        });
        return tickets;
    }

    public void disable(Guild guild) {
        Category cat = getCategory(guild);
        for(Channel ch : cat.getChannels())
            ch.delete().queue();
        cat.delete().queue();
    }

}
