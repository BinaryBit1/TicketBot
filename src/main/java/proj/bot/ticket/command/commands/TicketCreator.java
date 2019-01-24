package proj.bot.ticket.command.commands;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.supports.SupportType;
import proj.bot.ticket.supports.Ticket;
import proj.bot.ticket.utils.Messenger;

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
        
        ServerConfig config = new ServerConfig(guild.getId());
        if(config.blacklistContains(user.getId())) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You are currently blacklisted from creating tickets.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(user, embed.build());
            return;
        }
        
        SupportType type = SupportType.fromString(command);
        if(!config.getSupportType(type)) {
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