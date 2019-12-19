package proj.bot.ticket;

import java.awt.Color;
import java.io.File;

import javax.security.auth.login.LoginException;

import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import proj.api.marble.tasks.logwindow.LogListener;
import proj.api.marble.tasks.logwindow.LogWindow;
import proj.bot.ticket.config.TicketConfig;
import proj.bot.ticket.events.TicketListener;

public class TicketBot {
    @Getter
    private static TicketBot instance;
    
    @Getter
    private JDA jda;
    
    @Getter
    private LogWindow logger;
    
    @Getter
    private TicketConfig config;

    public static void main(String[] args) throws LoginException, InterruptedException {
        new TicketBot().init(args);
    }
    
    
    @SuppressWarnings("deprecation")
    public void init(String[] launchArgs) {
        instance = this;
        
        try {
            logger = new LogWindow("TicketBot", 800, 400);
            logger.addLogListener(new ListenerClass());
        } catch(Exception e) {
            System.out.println("Ignoring GUI implementation...");
        }
        
        System.out.println("Starting Bot, please wait...");
        String filePath;
        try { filePath = new File(TicketBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString()).getParent(); } catch(Exception e) {e.printStackTrace(); System.out.println("Error getting file path, shutting down."); return;}
        
        config = new TicketConfig(launchArgs, filePath, "TicketConfig.yml");

        for(String key : config.getVariables().keySet()) {
            System.out.println("USING " + key + ": " + config.getVariables().get(key));
        }
        
        try {
            getInstance().jda = new JDABuilder(AccountType.BOT).setToken(config.getVariables().get("BOT_TOKEN")).buildBlocking();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem starting the bot. Please try again.");
            return;
        }
        System.out.println("Bot started!");
        
        getInstance().jda.getPresence().setGame(Game.playing("Type: " + getPrefix() + "help"));
        getInstance().jda.addEventListener(new TicketListener());
    }

    public static Color defColor(Guild guild) {
        return guild.getMember(getInstance().jda.getSelfUser()).getColor();
    }
    
    public String getPrefix() {
        return config.getVariables().get("COMMAND_PREFIX");
    }
}

class ListenerClass implements LogListener {

    JDA jda(){return TicketBot.getInstance().getJda();}
    LogWindow logger = TicketBot.getInstance().getLogger();

    @Override
    public void onLogInput(String input) {
            logger.log("All bot interactions will now be in Discord. Nothing further to do here...");
            logger.log("If you wish to get help using the bot, type \"" + TicketBot.getInstance().getPrefix() + "help\" in the discord chat.");
            logger.log("If you wish to stop the bot, simply close the window.");
    }

}
