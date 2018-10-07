package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.Ticket;
import proj.bot.ticket.supports.ISupportType;

public class Bug implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Bug Reports";
    }

    @Override
    public String getHelpMessage() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("To Report A Bug: " + "*\"" + Ticket.getInstance().prefix + "bug\"*");
        
        return null;
    }

    @Override
    public void enable() {
        Catagory cat;
    }

    @Override
    public String getCatagoryName() {
        return Emoji.Bug + " Bug Reports";
    }

    @Override
    public String createTicket(User user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MessageChannel getTicket(String channelName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void closeTicket(String channelName) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void disable() {
        // TODO Auto-generated method stub
        
    }

}
