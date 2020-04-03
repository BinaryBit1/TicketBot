package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

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