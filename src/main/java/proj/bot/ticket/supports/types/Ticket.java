package proj.bot.ticket.supports.types;

import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Ticket implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable General Tickets";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.TICKET.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.PageFacingUp.getValue() + " Tickets";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "How can we help you?";
    }
    
}
