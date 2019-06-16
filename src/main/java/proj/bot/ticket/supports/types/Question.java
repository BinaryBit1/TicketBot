package proj.bot.ticket.supports.types;

import proj.api.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Question implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Question Support";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().getPrefix() + SupportType.QUESTION.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Question.getValue() + " Questions";
    }
    
    @Override
    public String getTicketCreatedMessage() {
        return "How can we help you?";
    }

}
