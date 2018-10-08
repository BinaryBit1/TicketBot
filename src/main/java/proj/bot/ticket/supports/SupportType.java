package proj.bot.ticket.supports;

import java.util.List;

import lombok.Getter;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.supports.types.Ban;
import proj.bot.ticket.supports.types.Bug;
import proj.bot.ticket.supports.types.Question;
import proj.bot.ticket.supports.types.Rank;
import proj.bot.ticket.supports.types.Rolee;
import proj.bot.ticket.supports.types.Suggest;
import proj.bot.ticket.supports.types.Support;

public enum SupportType {

    //Support types - Ban appeals, suggestions, bugs, questions, role request, support

    BAN("ban", new Ban()),
    BUG("bug", new Bug()),
    QUESTION("question", new Question()),
    RANK("rank", new Rank()),
    ROLE("role", new Rolee()),
    SUGGEST("suggest", new Suggest()),
    SUPPORT("support", new Support()),
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
    
    public static Role getSupportRole(Guild guild) {
        List<Role> roles = guild.getRolesByName("Support Specialist", false);
        if (!roles.isEmpty())
            return roles.get(0);
        else
            return guild.getController().createRole().setName("Support Specialist").complete();
    }
    
    public static SupportType getSupportType(Guild guild, String channelName) {
        for(SupportType type : new ServerConfig(guild.getId()).getEnabledSupports()) {
            if(type.getSupportType().getTicket(guild, channelName) != null)
                return type;
        }
        return null;
    }

}
