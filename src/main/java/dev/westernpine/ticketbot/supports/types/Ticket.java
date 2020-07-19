package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;

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
