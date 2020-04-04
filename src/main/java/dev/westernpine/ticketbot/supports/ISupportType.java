package dev.westernpine.ticketbot.supports;

public interface ISupportType {

    public String getConfigPath();
    
    public String getHelpMessage();
    
    public String getCategoryName();

    String getTicketCreatedMessage();

}
