package proj.bot.ticket.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.proj.marble.lib.config.Config;
import lombok.Getter;
import proj.bot.ticket.Ticket;
import proj.bot.ticket.supports.SupportType;

public class ServerConfig {

    @Getter
    private Config config;
    @Getter
    private String guildId;
    
    public ServerConfig(String guildId) {
        this.guildId = guildId;
        this.config = new Config(Ticket.filePath + File.separator + "Servers", guildId);
        
        for(SupportType type : SupportType.values()) {
            getSupportType(type);
        }
        
        this.getBlacklist();
        config.save();
    }
    
    public List<SupportType> getEnabledSupports() {
        List<SupportType> types = new ArrayList<>();
        for(SupportType type : SupportType.values()) {
            if(getSupportType(type)) {
                types.add(type);
            }
        }
        return types;
    }
    
    public boolean getSupportType(SupportType supportType) {
        return (boolean) config.get(supportType.getSupportType().getConfigPath(), false);
    }
    
    public void setSupportType(SupportType supportType, boolean enabled) {
        config.set(supportType.getSupportType().getConfigPath(), enabled);
    }
    
    public void addUserToBlacklist(String userid) {
        List<String> users = getBlacklist();
        users.remove(userid);
        users.add(userid);
        config.set("Blacklisted Users", users);
    }
    
    public void removeUserFromBlacklist(String userid) {
        List<String> users = getBlacklist();
            users.remove(userid);
        config.set("Blacklisted Users", users);
    }
    
    
    public boolean blacklistContains(String userid){
        List<String> users = getBlacklist();
        return users.contains(userid);
    }
    
    
    private List<String> getBlacklist(){
        return config.getList("Blacklisted Users", new ArrayList<>(), " ");
    }
    
    

}
