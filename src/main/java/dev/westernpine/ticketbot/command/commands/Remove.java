package dev.westernpine.ticketbot.command.commands;

import java.awt.Color;
import java.util.List;

import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.authenticator.Authenticator;
import dev.westernpine.ticketbot.command.Command;
import dev.westernpine.ticketbot.supports.Ticket;
import dev.westernpine.ticketbot.util.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import proj.api.marble.lib.emoji.Emoji;
import proj.api.marble.lib.uid.UID;

public class Remove implements Command {
    
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
        
        if(args.length != 1) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **Invalid arguement length.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        Ticket ticket = Ticket.from(guild, UID.from(ch.getName()));
        if(ticket == null) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be in a ticket channel to use this command.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        if(!Authenticator.isSupport(guild, user) && !ticket.getOwner().equals(user.getId())) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be the ticket owner, or part of the support staff, to remove members from this ticket.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        String tagged = args[0];
        if(!tagged.contains("#")){
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **Invalid tagged user. (ExampleUser#0000)**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        List<User> users = TicketBot.getInstance().getJda().getUsersByName(tagged.split("#")[0], false);
        User taggedUser = null;
        for(User u : users) {
            if(u.getDiscriminator().equals(tagged.split("#")[1])) {
                taggedUser = u;
            }
        }
        
        if(taggedUser == null) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **User not found.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        if(ticket.getOwner().equals(taggedUser.getId())) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You cannot remove the ticket owner.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        ticket.removeUser(taggedUser);
        
    }

}
