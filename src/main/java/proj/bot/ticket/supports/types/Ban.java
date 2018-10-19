package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;
import proj.bot.ticket.supports.SupportType;

public class Ban implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Ban Appeals";
    }

    @Override
    public String getHelpMessage() {
        return TicketBot.getInstance().prefix + SupportType.BAN.getString();
    }

    @Override
    public String getCategoryName() {
        return Emoji.Hammer.getValue() + " Ban Appeals";
    }

    @Override
    public String getTicketCreatedMessage() {
        return "Please explain the situation.";
    }
    
}
