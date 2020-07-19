package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.common.emoji.Emoji;
import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;

public class Suggest implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Suggestion Support";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.SUGGEST.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Pencil.getValue() + " Suggestions";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "What can we improve, and how?";
    }

}
