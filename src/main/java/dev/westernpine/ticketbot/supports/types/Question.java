package dev.westernpine.ticketbot.supports.types;

import dev.westernpine.ticketbot.TicketBot;
import dev.westernpine.ticketbot.supports.ISupportType;
import dev.westernpine.ticketbot.supports.SupportType;
import proj.api.marble.lib.emoji.Emoji;

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
