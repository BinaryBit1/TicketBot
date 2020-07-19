package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;

public class Support implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Support Requests";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.SUPPORT.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Fist.getValue() + " Support";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "How can we help you?";
    }

}
