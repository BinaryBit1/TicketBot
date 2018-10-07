package proj.bot.ticket.supports;

import lombok.Getter;
import proj.bot.ticket.supports.types.Bug;

public enum SupportType {

    //Support types - Ban appeals, suggestions, bugs, questions, role request, support
    
    //Support Specialist role to have access to the tickets
    
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

}
