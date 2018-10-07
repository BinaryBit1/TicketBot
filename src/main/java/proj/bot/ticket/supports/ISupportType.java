package proj.bot.ticket.supports;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public interface ISupportType {

    public String getConfigPath();
    
    public String getHelpMessage();
    
    public void enable(Guild guild);
    
    public String getCatagoryName(Guild guild);
    
    public String createTicket(Guild guild, User user);
    
    public MessageChannel getTicket(Guild guild, String channelName);
    
    public void closeTicket(Guild guild, String channelName);
    
    public void disable(Guild guild);
    
    
    

}
