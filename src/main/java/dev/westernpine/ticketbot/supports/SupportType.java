package dev.westernpine.ticketbot.supports;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import dev.westernpine.common.uid.UID;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.types.Ban;
import dev.westernpine.ticketbot.supports.types.Billing;
import dev.westernpine.ticketbot.supports.types.Bug;
import dev.westernpine.ticketbot.supports.types.Question;
import dev.westernpine.ticketbot.supports.types.Request;
import dev.westernpine.ticketbot.supports.types.Suggest;
import dev.westernpine.ticketbot.supports.types.Support;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public enum SupportType {

    BAN("ban", "bn", new Ban()),
    BILLING("billing", "blg", new Billing()),
    BUG("bug", "bg", new Bug()),
    QUESTION("question", "qst", new Question()),
    REQUEST("request", "rqst", new Request()),
    SUGGEST("suggest", "sgt", new Suggest()),
    SUPPORT("support", "spt", new Support()),
    TICKET("ticket", "tkt", new dev.westernpine.ticketbot.supports.types.Ticket()),
    ;
    
    @Getter
    private String string;
    
    @Getter
    private String abr;
    
    @Getter
    private ISupportType supportType;
    
    SupportType(String string, String abr, ISupportType supportType){
        this.string = string;
        this.abr = abr;
        this.supportType = supportType;
    }
    
    @Override
    public String toString() {
        return this.string;
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
                ? guild.createRole().setName("Support Specialist").complete()
                : guild.getRolesByName("Support Specialist", false).get(0));
    }
    public static EnumSet<Permission> getSupportAllow() { return EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE); }
    public static EnumSet<Permission> getSupportDeny() { return EnumSet.noneOf(Permission.class); }
    
    public static Member getSelfMember(Guild guild) { return guild.getMember(TicketBot.getInstance().getJda().getSelfUser()); }
    public static EnumSet<Permission> getSelfAllow() { return EnumSet.of(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS, Permission.MANAGE_CHANNEL, Permission.MANAGE_ROLES, Permission.MESSAGE_MANAGE); }
    public static EnumSet<Permission> getSelfDeny() { return EnumSet.noneOf(Permission.class); }
    
    public void enable(Guild guild) {
        getCategory(guild);
    }
    
    public Category getCategory(Guild guild) {
        List<Category> cats = guild.getCategoriesByName(supportType.getCategoryName(), true);
        String name = supportType.getCategoryName();
        if (cats.isEmpty())
            return (Category) guild.createCategory(name)
                    .addPermissionOverride(getPublicRole(guild), getPublicAllow(), getPublicDeny())
                    .addPermissionOverride(getSupportRole(guild), getSupportAllow(), getSupportDeny())
                    .addPermissionOverride(getSelfMember(guild), getSelfAllow(), getSelfDeny())
                    .complete();
        else
            return cats.get(0);
    }

    public List<Ticket> getTickets(Guild guild, String user) {
        List<TextChannel> channels = getCategory(guild).getTextChannels();
        List<Ticket> tickets = new ArrayList<>();
        channels.stream().forEach(channel -> {
            Ticket ticket = Ticket.from(channel);
            if (ticket != null) {
                if (ticket.getOwner().equals(user))
                    tickets.add(ticket);
            }
        });
        return tickets;
    }
    
    public boolean containsChannel(Guild guild, UID uid) {
        Category cat = getCategory(guild);
        List<TextChannel> channels = cat.getTextChannels();
        if(!channels.isEmpty()) {
            for(TextChannel ch : channels) {
                if(UID.from(ch.getName()).equals(uid))
                    return true;
            }
        }
        return false;
    }

    public void disable(Guild guild) {
        Category cat = getCategory(guild);
        for(GuildChannel ch : cat.getChannels())
            ch.delete().queue();
        cat.delete().queue();
    }

}
