package proj.bot.ticket.config;

import lombok.Getter;
import proj.api.marble.lib.config.Config;

public class TicketConfig {
    @Getter
    public Config config;
    
    public TicketConfig(String configPath, String fileName) {
        this.config = new Config(configPath, fileName);
        
        this.getToken();
        this.getPrefixIdentifier();
        config.save();
    }
    
    public String getToken() {
        return (String) config.get("Bot Token", "000000000000000.0000000000000000.000000000");
    }

    public String getPrefixIdentifier() {
        return (String) config.get("Command Prefix", "!");
    }

}
