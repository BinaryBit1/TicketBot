package proj.bot.ticket.supports;

import java.util.List;

import lombok.Getter;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import proj.bot.ticket.supports.types.Bug;

public enum SupportType {

    //Support types - Ban appeals, suggestions, bugs, questions, role request, support
    
    BUG("bug", new Bug()),
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
        return null;
    }
    
    public static SupportType getSupportType(Guild guild, String channelName) {
        for(SupportType type : SupportType.values()) {
            if(type.getSupportType().getTicket(guild, channelName) != null)
                return type;
        }
        return null;
    }

}
