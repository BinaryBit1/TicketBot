package dev.westernpine.supports.types;

import dev.westernpine.TicketBot;
import dev.westernpine.supports.ISupportType;
import dev.westernpine.supports.SupportType;
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
