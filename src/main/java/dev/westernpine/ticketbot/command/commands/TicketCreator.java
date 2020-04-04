package dev.westernpine.ticketbot.command.commands;

import java.awt.Color;

import dev.westernpine.ticketbot.authenticator.Authenticator;
import dev.westernpine.ticketbot.command.Command;
import dev.westernpine.ticketbot.sql.ServerTable;
import dev.westernpine.ticketbot.supports.SupportType;
import dev.westernpine.ticketbot.supports.Ticket;
import dev.westernpine.ticketbot.util.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import proj.api.marble.lib.emoji.Emoji;

public class TicketCreator implements Command {
    
    @Override
    public boolean permissible() {
        return false;
    }

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
        return null;
    }

    @Override
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args) {
        
        try { msg.delete().queue(); } catch(Exception e) {}
        
        ServerTable table = new ServerTable(guild.getId());
        if(table.blacklistContains(user.getId())) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You are currently blacklisted from creating tickets.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(user, embed.build());
            return;
        }
        
        SupportType type = SupportType.fromString(command);
        if(!table.getSupportType(type)) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **That support type is currently unavailable.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(user, embed.build());
            return;
        }
        
        if(type.getTickets(guild, user.getId()).size() >= 5 && !Authenticator.isSupport(guild, user)) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You have too many tickets open under that type.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(user, embed.build());
            return;
        }
        
        Ticket ticket = new Ticket(type, guild, user.getId());
        ticket.create();
    }

}