package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

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
