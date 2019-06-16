package proj.bot.ticket.supports.types;

import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Bug implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Bug Reports";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.BUG.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Bug.getValue() + " Bug Reports";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "Please describe the bug in detail, and how it was discovered.";
    }

}
