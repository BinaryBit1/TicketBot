package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

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
