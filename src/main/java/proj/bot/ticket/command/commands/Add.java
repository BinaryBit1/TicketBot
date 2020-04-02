package proj.bot.ticket.command.commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import proj.api.marble.lib.emoji.Emoji;
import proj.api.marble.lib.uid.UID;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.supports.Ticket;
import proj.bot.ticket.utils.Messenger;

public class Add implements Command {
    
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
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be the ticket owner, or part of the support staff, to add members to this ticket.**");
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
        
        ticket.addUser(taggedUser);
        
    }

}
