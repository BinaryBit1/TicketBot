package dev.westernpine.command.commands;

import java.awt.Color;

import dev.westernpine.command.Command;
import dev.westernpine.utils.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import proj.api.marble.lib.emoji.Emoji;

public class NoPermission implements Command {
    
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
        
      EmbedBuilder embed = Messenger.getEmbedFrame();
      embed.setDescription(Emoji.CrossMark.getValue() + " **I was unable to perform that action for you.**");
      embed.setColor(Color.RED);
      Messenger.sendEmbed(user, embed.build());
    }

}
