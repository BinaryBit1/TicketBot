package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;

public class Ban implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Ban Appeals";
    }

    @Override
    public String getHelpMessage() {
        return "To submit a ban appeal: " + "*\"" + TicketBot.getInstance().prefix + "ban\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Hammer.getValue() + " Ban Appeals";
    }

    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease explain the situation.";
    }
    
}
