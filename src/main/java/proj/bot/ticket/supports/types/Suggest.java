package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
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
        return "To submit a suggestion: " + "*\"" + TicketBot.getInstance().prefix + SupportType.SUGGEST + "\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Pencil.getValue() + " Suggestions";
    }

    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease describe your suggestion in detail.";
    }

}
