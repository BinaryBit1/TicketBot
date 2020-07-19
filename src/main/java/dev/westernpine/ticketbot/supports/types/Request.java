package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;

public class Request implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Requests";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.REQUEST.getString();
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