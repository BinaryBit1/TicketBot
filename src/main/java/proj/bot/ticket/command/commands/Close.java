package proj.bot.ticket.command.commands;

import java.awt.Color;

import api.proj.marble.lib.emoji.Emoji;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.supports.SupportType;
import proj.bot.ticket.utils.Messenger;

public class Close implements Command {
    
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
        
        SupportType type = SupportType.getSupportType(guild, ch.getName());
        if(type == null) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be in a ticket channel to use this command.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        if(!type.getSupportType().getOwner((TextChannel) ch).getId().equals(user.getId()) && !guild.getMember(user).getRoles().contains(SupportType.getSupportRole(guild)) && !Authenticator.hasPermission(guild, Permission.ADMINISTRATOR, user)) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **You must be the ticket owner, or a support staff, to close this ticket.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        type.getSupportType().closeTicket((TextChannel) ch);
        
    }

}
