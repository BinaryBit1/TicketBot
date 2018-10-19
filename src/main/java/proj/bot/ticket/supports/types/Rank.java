package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Rank implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Rank Requests";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().prefix + SupportType.RANK.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Gem.getValue() + " Rank Requests";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "What rank are you requesting?";
    }
    
}