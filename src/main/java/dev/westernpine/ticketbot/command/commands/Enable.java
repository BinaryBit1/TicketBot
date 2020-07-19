package dev.westernpine.ticketbot.command.commands;

import java.awt.Color;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.authenticator.Authenticator;
import dev.westernpine.ticketbot.command.Command;
import dev.westernpine.ticketbot.command.CommandExecutor;
import dev.westernpine.ticketbot.sql.ServerTable;
import dev.westernpine.ticketbot.supports.SupportType;
import dev.westernpine.ticketbot.util.Messenger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

public class Enable implements Command {
    
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
        
        new ServerTable(guild.getId()).setSupportType(type, true);
        type.enable(guild);
        
        EmbedBuilder embed = Messenger.getEmbedFrame();
        embed.setDescription(Emoji.GreenCheck.getValue() + " **Support type enabled.**");
        embed.setColor(Color.GREEN);
        Messenger.sendEmbed(ch, embed.build());
        
        
    }

}
