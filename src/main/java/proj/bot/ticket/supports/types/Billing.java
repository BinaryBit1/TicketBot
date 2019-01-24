package proj.bot.ticket.supports.types;

import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Billing implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Billing Inquiries";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().prefix + SupportType.BILLING.getString();
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
