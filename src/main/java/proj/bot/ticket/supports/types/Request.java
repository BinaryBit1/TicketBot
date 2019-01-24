package proj.bot.ticket.supports.types;

import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Request implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Requests";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().prefix + SupportType.REQUEST.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Exclamation.getValue() + " Requests";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "What are you requesting?";
    }
    
}