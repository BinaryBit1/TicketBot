package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;

public class Bug implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Bug Reports";
    }

    @Override
    public String getHelpMessage() {
        return "To report a bug: " + "*\"" + TicketBot.getInstance().prefix + "bug\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Bug.getValue() + " Bug Reports";
    }

    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease describe the bug in detail, and how it was discovered.";
    }

}
