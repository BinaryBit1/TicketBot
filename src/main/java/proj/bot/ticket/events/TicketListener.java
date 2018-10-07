package proj.bot.ticket.events;

import java.util.Arrays;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import proj.bot.ticket.Ticket;
import proj.bot.ticket.command.CommandExecutor;

public class TicketListener extends ListenerAdapter {

    public static final String prefix = Ticket.getInstance().prefix;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel ch = event.getChannel();
        if (msg.getContentRaw().startsWith(prefix)) {
            User user = event.getAuthor();
            String command = msg.getContentRaw().split(" ")[0].replaceAll(prefix, "");
            String[] args = Arrays.copyOfRange(msg.getContentRaw().split(" "), 1, msg.getContentRaw().split(" ").length);
            CommandExecutor.getCommand(command, msg.getGuild(), user).execute(msg.getGuild(), user, ch, msg, command, args);
        }
    }
}