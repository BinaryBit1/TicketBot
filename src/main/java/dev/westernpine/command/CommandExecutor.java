package dev.westernpine.command;

import dev.westernpine.authenticator.Authenticator;
import dev.westernpine.command.commands.Add;
import dev.westernpine.command.commands.Blacklist;
import dev.westernpine.command.commands.Close;
import dev.westernpine.command.commands.Disable;
import dev.westernpine.command.commands.Enable;
import dev.westernpine.command.commands.Help;
import dev.westernpine.command.commands.Leave;
import dev.westernpine.command.commands.NA;
import dev.westernpine.command.commands.NoPermission;
import dev.westernpine.command.commands.Remove;
import dev.westernpine.command.commands.TicketCreator;
import dev.westernpine.supports.SupportType;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

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
