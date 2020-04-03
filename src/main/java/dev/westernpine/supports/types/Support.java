package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

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
