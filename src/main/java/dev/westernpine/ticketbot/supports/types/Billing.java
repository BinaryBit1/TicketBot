package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;
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
