package proj.bot.ticket.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.TicketBot;

public class Messenger {
    
    public static void sendMessage(MessageChannel ch, String message) {
        ch.sendMessage(message).queue();
    }
    
    public static void sendEmbed(MessageChannel ch, MessageEmbed embed) {
        ch.sendMessage(embed).queue();
    }
    
    public static void sendMessage(User user, String message) {
        user.openPrivateChannel().queue(ch -> ch.sendMessage(message).complete());
    }
    
    public static void sendEmbed(User user, MessageEmbed embed) {
        user.openPrivateChannel().queue(ch -> ch.sendMessage(embed).complete());
    }
    
    public static EmbedBuilder getEmbedFrame(Guild guild) {
        return new EmbedBuilder().setColor(TicketBot.defColor(guild));
    }
    
    public static EmbedBuilder getEmbedFrame() {
        return new EmbedBuilder();
    }

}
