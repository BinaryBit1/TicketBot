package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
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
        return "To inquire about billing: " + "*\"" + TicketBot.getInstance().prefix + SupportType.BILLING + "\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Dollar.getValue() + " Billing Inquiries";
    }

    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease explain how you can be helped.";
    }
    
}
