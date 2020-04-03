package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

public class Billing implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Billing Inquiries";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.BILLING.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Dollar.getValue() + " Billing Inquiries";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "How can we help you?";
    }
    
}
