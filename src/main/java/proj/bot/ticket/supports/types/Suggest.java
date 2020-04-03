package proj.bot.ticket.supports.types;

import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Suggest implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Suggestion Support";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.SUGGEST.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Pencil.getValue() + " Suggestions";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "What can we improve, and how?";
    }

}
