package proj.bot.ticket.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public interface Command {
    
    public boolean useRole();
    
    public String getRole();
    
    public Permission getPermission();
    
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args);

}
