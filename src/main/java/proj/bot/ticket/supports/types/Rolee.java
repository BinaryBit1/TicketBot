package proj.bot.ticket.supports.types;

import api.proj.marble.lib.emoji.Emoji;
import proj.bot.ticket.TicketBot;
import proj.bot.ticket.supports.ISupportType;

public class Rolee implements ISupportType {

    @Override
    public String getConfigPath() {
        return "Enable Role Requests";
    }

    @Override
    public String getHelpMessage() {
        return "To request a role: " + "*\"" + TicketBot.getInstance().prefix + "role\"*";
    }

    @Override
    public String getCategoryName() {
        return Emoji.Scroll.getValue() + " Role Requests";
    }

    @Override
    public String getTicketCreatedMessage() {
        return Emoji.PageFacingUp.getValue() + " Ticket created! \n\nPlease describe the purpose of this request.";
    }
    
}