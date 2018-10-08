package proj.bot.ticket.command;

import lombok.Getter;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.commands.Add;
import proj.bot.ticket.command.commands.Blacklist;
import proj.bot.ticket.command.commands.Close;
import proj.bot.ticket.command.commands.Disable;
import proj.bot.ticket.command.commands.Enable;
import proj.bot.ticket.command.commands.Help;
import proj.bot.ticket.command.commands.Leave;
import proj.bot.ticket.command.commands.NA;
import proj.bot.ticket.command.commands.NoPermission;
import proj.bot.ticket.command.commands.Remove;
import proj.bot.ticket.command.commands.TicketCreator;
import proj.bot.ticket.supports.SupportType;

public enum CommandExecutor {
    ADD("ADD", new Add()),
    BLACKLIST("BLACKLIST", new Blacklist()),
    CLOSE("CLOSE", new Close()),
    DISABLE("DISABLE", new Disable()),
    ENABLE("ENABLE", new Enable()),
    HELP("HELP", new Help()),
    LEAVE("LEAVE", new Leave()),
    NA("NA", new NA()),
    NP("NP", new NoPermission()),
    REMOVE("REMOVE", new Remove()),
    TICKET_CREATOR("TICKET_CREATOR", new TicketCreator()),
    ;
    
    @Getter
    private String identifier;
    
    @Getter
    private Command command;
    
    CommandExecutor(String identifier, Command command) {
        this.identifier = identifier;
        this.command = command;
    }
    
    public static CommandExecutor getCommand(String cmd, Guild guild, User user) {        
        for(CommandExecutor ce : CommandExecutor.values()) {
            if(ce.getIdentifier().equalsIgnoreCase(cmd)) {
                
                if(ce.getCommand().permissible()) {
                    if(ce.getCommand().useRole()) {
                        if(Authenticator.hasRole(guild, ce.getCommand().getRole(), user)) {
                            return ce;
                        } else {
                            return NP;
                        }
                    }else {
                        if(Authenticator.hasPermission(guild, ce.getCommand().getPermission(), user)) {
                            return ce;
                        } else {
                            return NP;
                        }
                    }
                } else {
                    return ce;
                }
            } else if(SupportType.fromString(cmd) != null) {
                return TICKET_CREATOR;
            }
        }
        return NA;
    }
    
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args) {
        if(this.equals(CommandExecutor.NA))
            return;
        getCommand().execute(guild, user, ch, msg, command, args);
    }
}
