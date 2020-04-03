package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

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
