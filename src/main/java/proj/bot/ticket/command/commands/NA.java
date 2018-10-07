package proj.bot.ticket.command.commands;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.command.Command;

public class NA implements Command {

    @Override
    public boolean useRole() {
        return false;
    }

    @Override
    public String getRole() {
        return null;
    }

    @Override
    public Permission getPermission() {
        return Permission.MESSAGE_WRITE;
    }

    @Override
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args) {
        
    }

}
