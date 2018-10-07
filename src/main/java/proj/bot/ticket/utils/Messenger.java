package proj.bot.ticket.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.Ticket;

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
        User maker = Ticket.getInstance().getJda().getUserById("92191261095636992");
        return new EmbedBuilder().setColor(Ticket.defColor(guild)).setFooter(maker.getName() + " Powered!" + " (" + maker.getName() + "#" + maker.getDiscriminator() + ")", maker.getAvatarUrl());
    }
    
    public static EmbedBuilder getEmbedFrame() {
        User maker = Ticket.getInstance().getJda().getUserById("92191261095636992");
        return new EmbedBuilder().setFooter(maker.getName() + " Powered!" + " (" + maker.getName() + "#" + maker.getDiscriminator() + ")", maker.getAvatarUrl());
    }

}
