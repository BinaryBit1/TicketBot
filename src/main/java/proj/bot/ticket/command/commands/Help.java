package proj.bot.ticket.command.commands;

import java.util.List;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.authenticator.Authenticator;
import proj.bot.ticket.command.Command;
import proj.bot.ticket.sql.ServerTable;
import proj.bot.ticket.supports.SupportType;
import proj.bot.ticket.utils.Messenger;

public class Help implements Command {
    
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
        
        StringBuilder sb = new StringBuilder();

        sb.append("__**TicketBot Help Menu**__").append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));

        if (Authenticator.isSupport(guild, user)) {
            sb.append("**Support Staff & Administrators**").append(System.getProperty("line.separator"));
            sb.append("Administrators and memebers with the \"Support Specialist\" role:").append(System.getProperty("line.separator"));
            sb.append(" - May modify the blacklisted users.").append(System.getProperty("line.separator"));
            sb.append(" - May create unlimited tickets.").append(System.getProperty("line.separator"));
            sb.append(" - Have access to all tickets.").append(System.getProperty("line.separator"));
            sb.append(" - May modify ticket members.").append(System.getProperty("line.separator"));
            sb.append(" - May close any tickets.").append(System.getProperty("line.separator"));
            sb.append("Blacklist Commands").append(System.getProperty("line.separator"));
            sb.append(" - " + TicketBot.getInstance().getPrefix() + "blacklist add ExampleUser#0000").append(System.getProperty("line.separator"));
            sb.append(" - " + TicketBot.getInstance().getPrefix() + "blacklist remove ExampleUser#0000").append(System.getProperty("line.separator"));
            sb.append("By default, all support types are disabled for your server.").append(System.getProperty("line.separator"));
            sb.append("Support Type Commands").append(System.getProperty("line.separator"));
            sb.append(" - " + TicketBot.getInstance().getPrefix() + "enable (Support Type)").append(System.getProperty("line.separator"));
            sb.append(" - " + TicketBot.getInstance().getPrefix() + "disable (Support Type)").append(System.getProperty("line.separator"));
            sb.append("Support Types:").append(System.getProperty("line.separator"));
            for(SupportType type : SupportType.values()) {
                sb.append(" - " + type.getString()).append(System.getProperty("line.separator"));
            }
            sb.append(System.getProperty("line.separator"));
        }
        
        ServerTable table = new ServerTable(guild.getId());
        List<SupportType> enabledSupports = table.getEnabledSupports();
        if(enabledSupports.isEmpty()) {
            sb.append("There are currently no Support Types enabled for this server.");
        } else {
            sb.append("**Ticket Commands**").append(System.getProperty("line.separator"));
            sb.append("You are allowed a maximum of 5 tickets per type.").append(System.getProperty("line.separator"));
            sb.append("Close ticket: " + TicketBot.getInstance().getPrefix() + "close").append(System.getProperty("line.separator"));
            sb.append("Leave ticket: " + TicketBot.getInstance().getPrefix() + "leave").append(System.getProperty("line.separator"));
            sb.append("Add user: " + TicketBot.getInstance().getPrefix() + "add ExampleUser#0000").append(System.getProperty("line.separator"));
            sb.append("Remove user: " + TicketBot.getInstance().getPrefix() + "remove ExampleUser#0000").append(System.getProperty("line.separator"));
            sb.append("Support commands:").append(System.getProperty("line.separator"));
            for(SupportType type : enabledSupports) {
                sb.append(" - " + type.getSupportType().getHelpMessage()).append(System.getProperty("line.separator"));
            }
        }

        EmbedBuilder embed = Messenger.getEmbedFrame();
        embed.setDescription(sb.toString());
        User maker = TicketBot.getInstance().getJda().getUserById("559027677017669661");
        embed.setFooter(maker.getName() + " Powered!" + " (" + maker.getName() + "#" + maker.getDiscriminator() + ")", maker.getAvatarUrl());
        Messenger.sendEmbed(user, embed.build());
    }

}