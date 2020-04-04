package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

public class Bug implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Bug Reports";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.BUG.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Bug.getValue() + " Bug Reports";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "Please describe the bug in detail, and how it was discovered.";
    }

}
