package proj.bot.ticket.command;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.commands.Help;
import proj.bot.ticket.command.commands.NA;
import proj.bot.ticket.command.commands.NoPermission;
import proj.bot.ticket.command.commands.TicketCreator;
import proj.bot.ticket.supports.SupportType;

public enum CommandExecutor {
    NA("NA", new NA()),
    NP("NP", new NoPermission()),
    HELP("HELP", new Help()),
    
    TICKET_CREATOR("TICKET_CREATOR", new TicketCreator()),
    ;
    
    private String identifier;
    private Command command;
    
    CommandExecutor(String identifier, Command command) {
        this.identifier = identifier;
        this.command = command;
    }
    
    public static CommandExecutor getCommand(String cmd, Guild guild, User user) {
        for(CommandExecutor ce : CommandExecutor.values()) {
            if(ce.getIdentifier().equalsIgnoreCase(cmd)) {
               if(ce.getCommandClass().useRole()) {
                   if(Authenticator.hasRole(guild, ce.getCommandClass().getRole(), user)) {
                       return ce;
                   } else {
                       return NP;
                   }
               }else {
                   if(Authenticator.hasPermission(guild, ce.getCommandClass().getPermission(), user)) {
                       return ce;
                   } else {
                       return NP;
                   }
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
        getCommandClass().execute(guild, user, ch, msg, command, args);
    }
    
    public String getIdentifier() {
        return identifier;
    }
    
    public Command getCommandClass() {
        return command;
    }
}
