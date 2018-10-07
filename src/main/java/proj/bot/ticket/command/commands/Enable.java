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
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.supports.SupportType;
import proj.bot.ticket.utils.Messenger;

public class Enable implements Command {
    
    @Override
    public boolean permissible() {
        return true;
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
        return Permission.ADMINISTRATOR;
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
        
        SupportType type = SupportType.fromString(args[0]);
        if(type == null) {
            EmbedBuilder embed = Messenger.getEmbedFrame();
            embed.setDescription(Emoji.CrossMark.getValue() + " **Invalid support type.**");
            embed.setColor(Color.RED);
            Messenger.sendEmbed(ch, embed.build());
            return;
        }
        
        new ServerConfig(guild.getId()).setSupportType(type, true);
        type.getSupportType().enable(guild);
        
        EmbedBuilder embed = Messenger.getEmbedFrame();
        embed.setDescription(Emoji.GreenCheck.getValue() + " **Support type enabled.**");
        embed.setColor(Color.GREEN);
        Messenger.sendEmbed(ch, embed.build());
        
        
    }

}
