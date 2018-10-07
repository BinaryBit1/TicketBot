package proj.bot.ticket.supports;

import java.util.List;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public interface ISupportType {

    public String getConfigPath();
    
    public String getHelpMessage();
    
    public void enable(Guild guild);
    
    public Category getCategory(Guild guild);
    
    public String getCategoryName(Guild guild);
    
    public String createTicket(Guild guild, User user);
    
    public List<TextChannel> getTickets(Guild guild, User user);
    
    public TextChannel getTicket(Guild guild, String channelName);
    
    public User getOwner(TextChannel ch);
    
    public void closeTicket(TextChannel ch);
    
    public void addUserToTicket(TextChannel ch, User user);
    
    public void removeUserFromTicket(TextChannel ch, User user);
    
    public void disable(Guild guild);
    
    
    

}
