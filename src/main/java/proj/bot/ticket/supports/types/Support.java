package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Support implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Support Requests";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().prefix + SupportType.SUPPORT.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Fist.getValue() + " Support";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "How can we help you?";
    }

}
