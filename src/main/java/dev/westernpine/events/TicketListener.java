package dev.westernpine.events;

import java.util.Arrays;

import dev.westernpine.TicketBot;
import dev.westernpine.command.CommandExecutor;
import dev.westernpine.sql.ServerTable;
import dev.westernpine.supports.SupportType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketListener extends ListenerAdapter {

    public static final String prefix = TicketBot.getInstance().getPrefix();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        MessageChannel ch = event.getChannel();
        if (msg.getContentRaw().startsWith(prefix)) {
            User user = event.getAuthor();
            String command = msg.getContentRaw().split(" ")[0].replaceAll(prefix, "");
            String[] args = Arrays.copyOfRange(msg.getContentRaw().split(" "), 1,
                    msg.getContentRaw().split(" ").length);
            CommandExecutor.getCommand(command, msg.getGuild(), user).execute(msg.getGuild(), user, ch, msg, command,
                    args);
        }
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        SupportType.getSupportRole(event.getGuild());
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        String id = event.getUser().getId();
        new ServerTable(event.getGuild().getId()).getEnabledSupports().stream().forEach(type -> {
            type.getTickets(event.getGuild(), id).stream().forEach(ticket -> {
                ticket.close();
            });
        });
    }
}