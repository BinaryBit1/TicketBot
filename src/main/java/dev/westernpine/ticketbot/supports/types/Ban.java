package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;

public class Ban implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Ban Appeals";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.BAN.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Hammer.getValue() + " Ban Appeals";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "Please explain the situation.";
    }
    
}
