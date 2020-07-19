package dev.westernpine.ticketbot.command.commands;

import java.awt.Color;
import java.util.List;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.authenticator.Authenticator;
import dev.westernpine.ticketbot.command.Command;
import dev.westernpine.ticketbot.command.CommandExecutor;
import dev.westernpine.ticketbot.sql.ServerTable;
import dev.westernpine.ticketbot.util.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Blacklist implements Command {
    
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
        
        if(!Authenticator.isSupport(guild, user)){
            CommandExecutor.NP.execute(guild, user, ch, msg, command, args);
        }
        
        if(args.length != 2) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **Invalid arguement length.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        boolean add = args[0].equalsIgnoreCase("add");
        boolean remove = args[0].equalsIgnoreCase("remove");
        if(add == remove) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **Invalid action type.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        String tagged = args[1];
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
        
        ServerTable table = new ServerTable(guild.getId());        
        if(add) {
            table.updateBlacklist(taggedUser.getId(), true);
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.GreenCheck.getValue() + " **User added to blacklist.**");
            embed.setColor(Color.GREEN);
            Messenger.sendEmbed(ch, embed.build());
        } else {
            table.updateBlacklist(taggedUser.getId(), false);
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.GreenCheck.getValue() + " **User removed from blacklist.**");
            embed.setColor(Color.GREEN);
            Messenger.sendEmbed(ch, embed.build());
        }
        
        
    }
}