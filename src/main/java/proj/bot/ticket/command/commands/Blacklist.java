package proj.bot.ticket.command.commands;

import java.awt.Color;
import java.util.List;

import api.proj.marble.lib.emoji.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.Ticket;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.utils.Messenger;

public class Blacklist implements Command {

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
        return Permission.ADMINISTRATOR;
    }

    @Override
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args) {
        
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
        
        List<User> users = Ticket.getInstance().getJda().getUsersByName(tagged.split("#")[0], false);
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
        
        ServerConfig config = new ServerConfig(guild.getId());        
        if(add) {
            config.getBlacklist().remove(taggedUser.getId());
            config.getBlacklist().add(taggedUser.getId());
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.GreenCheck.getValue() + " **User added to blacklist.**");
            embed.setColor(Color.GREEN);
            Messenger.sendEmbed(user, embed.build());
        } else {
            config.getBlacklist().remove(taggedUser.getId());
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.GreenCheck.getValue() + " **User removed from blacklist.**");
            embed.setColor(Color.GREEN);
            Messenger.sendEmbed(user, embed.build());
        }
        
        
    }
}