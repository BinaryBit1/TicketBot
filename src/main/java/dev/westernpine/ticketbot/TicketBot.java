package dev.westernpine.ticketbot;

import java.awt.Color;
import java.io.File;
import java.util.EnumSet;

import javax.security.auth.login.LoginException;

import dev.westernpine.ticketbot.config.TicketConfig;
import dev.westernpine.ticketbot.events.TicketListener;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class TicketBot {
    @Getter
    private static TicketBot instance;
    
    @Getter
    private JDA jda;
    
    @Getter
    private TicketConfig config;

    public static void main(String[] args) throws LoginException, InterruptedException {
        new TicketBot().init(args);
    }
    
    
    public void init(String[] launchArgs) {
        instance = this;
        
        System.out.println("Starting Bot, please wait...");
        String filePath;
        try { filePath = new File(TicketBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString()).getParent(); } catch(Exception e) {e.printStackTrace(); System.out.println("Error getting file path, shutting down."); return;}
        
        config = new TicketConfig(launchArgs, filePath, "TicketConfig.yml");

        for(String key : config.getVariables().keySet()) {
            System.out.println("USING " + key + ": " + config.getVariables().get(key));
        }
        
        try {
        	EnumSet<GatewayIntent> intents = EnumSet.of(GatewayIntent.GUILD_MEMBERS, GatewayIntent.values());
        	EnumSet<CacheFlag> flags = EnumSet.of(CacheFlag.ACTIVITY, CacheFlag.values());
        	JDABuilder builder = JDABuilder.create(config.getVariables().get("BOT_TOKEN"), intents);
        	builder.enableCache(flags);
        	builder.setChunkingFilter(ChunkingFilter.ALL);
        	jda = builder.build();
        	jda.awaitReady();
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(0);
        }
        
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.listening("for \"" + getPrefix() + "help\""));
        jda.addEventListener(new TicketListener());
    }

    public static Color defColor(Guild guild) {
        return guild.getMember(getInstance().jda.getSelfUser()).getColor();
    }
    
    public String getPrefix() {
        return config.getVariables().get("COMMAND_PREFIX");
    }
}