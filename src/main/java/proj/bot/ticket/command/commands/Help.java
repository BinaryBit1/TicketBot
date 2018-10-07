package proj.bot.ticket.command.commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.Ticket;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.supports.SupportType;
import proj.bot.ticket.utils.Messenger;

public class Help implements Command {

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
        
        boolean isAdmin = Authenticator.hasPermission(guild, Permission.ADMINISTRATOR, user);
        StringBuilder sb = new StringBuilder();

        sb.append("__**Ticket Bot Help Menu**__").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));

        if (isAdmin) {
            sb.append("**Administration**").append(System.getProperty("line.separator"));
            sb.append("Administrating this bot is fairly simple.").append(System.getProperty("line.separator"));
            sb.append("By default, all support types are disabled for your server.").append(System.getProperty("line.separator"));
            sb.append("To add a player to the Ticket Blacklist, issue the command \"" + Ticket.getInstance().prefix + "blacklist add ExampleUser#0000\".").append(System.getProperty("line.separator"));
            sb.append("To remove a player from the Ticket Blacklist, issue the command \"" + Ticket.getInstance().prefix + "blacklist remove ExampleUser#0000\".").append(System.getProperty("line.separator"));
            sb.append("To enable a support type and set it up, issue the command: \"" + Ticket.getInstance().prefix + "enable (Support Type)\"").append(System.getProperty("line.separator"));
            sb.append("To enable a support type and set it up, issue the command: \"" + Ticket.getInstance().prefix + "enable (Support Type)\"").append(System.getProperty("line.separator"));
            sb.append("To disable the setting and remove it, issue the command: \"" + Ticket.getInstance().prefix + "disable (Support Type)\"").append(System.getProperty("line.separator"));
            sb.append("For support staff to have acess to tickets, they ***MUST*** have a role named: \"Support Specialist\"").append(System.getProperty("line.separator"));
            sb.append("All Support Types:").append(System.getProperty("line.separator"));
            for(SupportType type : SupportType.values()) {
                sb.append(" - " + type.getString()).append(System.getProperty("line.separator"));
            }
            sb.append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        }
        
        ServerConfig config = new ServerConfig(guild.getId());
        List<SupportType> enabledSupports = config.getEnabledSupports();
        if(enabledSupports.isEmpty()) {
            sb.append("There are currently no Support Types enabled for this server.");
        } else {
            sb.append("**Creating a ticket**").append(System.getProperty("line.separator"));
            sb.append("Creating a ticket is extremely easy!").append(System.getProperty("line.separator"));
            sb.append("Simply type the command, and a new ticket is created.").append(System.getProperty("line.separator"));
            sb.append("To add a user to the ticket, simply type: \"" + Ticket.getInstance().prefix + "add ExampleUser#0000\" in the ticket channel.").append(System.getProperty("line.separator"));
            sb.append("To remove a user from the ticket, simply type: \"" + Ticket.getInstance().prefix + "remove ExampleUser#0000\" in the ticket channel.").append(System.getProperty("line.separator"));
            sb.append("If you wish to close your ticket, type: \"" + Ticket.getInstance().prefix + "close\" in the ticket channel.").append(System.getProperty("line.separator"));
            sb.append("You are allowed a maximum of 5 tickets per type.").append(System.getProperty("line.separator"));
            sb.append("Support commands:").append(System.getProperty("line.separator"));
            for(SupportType type : enabledSupports) {
                sb.append(" - " + type.getSupportType().getHelpMessage()).append(System.getProperty("line.separator"));
            }
        }

        EmbedBuilder embed = Messenger.getEmbedFrame();
        embed.setDescription(sb.toString());
        embed.setColor(Color.RED);
        Messenger.sendEmbed(user, embed.build());
    }

}