package dev.westernpine.ticketbot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public interface Command {
    
    public boolean permissible();
    
    public boolean useRole();
    
    public String getRole();
    
    public Permission getPermission();
    
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args);

}
