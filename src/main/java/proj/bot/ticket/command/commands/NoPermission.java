package proj.bot.ticket.command.commands;

import java.awt.Color;

import api.proj.marble.lib.emoji.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.utils.Messenger;

public class NoPermission implements Command {

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
        return Permission.MESSAGE_WRITE;
    }

    @Override
    public void execute(Guild guild, User user, MessageChannel ch, Message msg, String command, String[] args) {
        
      try { msg.delete().queue(); } catch(Exception e) {}
        
      EmbedBuilder embed = Messenger.getEmbedFrame();
      embed.setDescription(Emoji.CrossMark.getValue() + " **" + "I was unable to perform that action for you." + "**");
      embed.setColor(Color.RED);
      Messenger.sendEmbed(user, embed.build());
    }

}
