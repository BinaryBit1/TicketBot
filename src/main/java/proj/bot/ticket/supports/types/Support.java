package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;

public class Support implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Support Requests";
    }

    @Override
    public String getHelpMessage() {
        return "To request support: " + "*\"" + TicketBot.getInstance().prefix + "support\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Fist.getValue() + " Support";
    }

    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease explain how you can be helped.";
    }

}
