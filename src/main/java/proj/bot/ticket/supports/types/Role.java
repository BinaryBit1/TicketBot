package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Role implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Role Requests";
    }

    @Override
    public String getHelpMessage() {
        return "To request a role: " + "*\"" + TicketBot.getInstance().prefix + SupportType.ROLE.getString() + "\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Scroll.getValue() + " Role Requests";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "What role and you requesting?";
    }
    
}