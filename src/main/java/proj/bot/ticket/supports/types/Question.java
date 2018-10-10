package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;

public class Question implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Question Support";
    }

    @Override
    public String getHelpMessage() {
        return "To submit a question: " + "*\"" + TicketBot.getInstance().prefix + "question\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Question.getValue() + " Questions";
    }
    
    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease keep your question brief, and to the point.";
    }

}
