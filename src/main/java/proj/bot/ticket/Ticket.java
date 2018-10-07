package proj.bot.ticket;

import java.awt.Color;
import java.io.File;

import javax.security.auth.login.LoginException;

import api.proj.marble.tasks.logwindow.LogListener;
import api.proj.marble.tasks.logwindow.LogWindow;
import api.proj.marble.tasks.threading.ThreadManager;
import api.proj.marble.tasks.threading.Threadder;
import lombok.Getter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import proj.bot.ticket.config.TicketConfig;
import proj.bot.ticket.events.TicketListener;

public class Ticket {

    public static String filePath;
    
    @Getter
    private static Ticket instance;
    @Getter
    private JDA jda;
    public LogWindow logger;
    
    public String prefix;

    public boolean initiated = false;

    public static void main(String[] args) throws LoginException, InterruptedException {
        if(args.length > 0 && args[0].equalsIgnoreCase("nogui")) {
            new Ticket().enable();
        } else {
            new Ticket().enableGUI();
        }
    }
    
    public void enable() {
        instance = this;
        init();
    }
    
    public void enableGUI() {
        instance = this;
        logger = new LogWindow("PandaWorkoutBot", 800, 400);
        logger.addLogListener(new ListenerClass());
        init();
    }
    
    @SuppressWarnings("deprecation")
    public void init() {
        System.out.println("Starting Bot, please wait...");
        
        try { filePath = new File(Ticket.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString()).getParent(); } catch(Exception e) {e.printStackTrace(); System.out.println("Error getting file path, shutting down."); return;}
        
        TicketConfig config = new TicketConfig(filePath, "SteamyConfig.yml");
        prefix = config.getPrefixIdentifier();
        
        try {
            getInstance().jda = new JDABuilder(AccountType.BOT).setToken(config.getToken()).buildBlocking();
        } catch (Exception e) {
            System.out.println("There was a problem starting the bot. Please try again.");
            return;
        }
        getInstance().initiated = true;
        System.out.println("Bot started!");
        
        setPlayingLoop();
        getInstance().jda.addEventListener(new TicketListener());
    }
    
    public void setPlayingLoop() {
        getInstance().jda.getPresence().setGame(Game.playing("Type: " + prefix + "help"));
        ThreadManager.callNewThread(new Threadder() {
            @Override
            public void run() {
                Ticket.getInstance().setPlayingLoop();
            }
        }, 86400000);
    }

    public static Color defColor(Guild guild) {
        return guild.getMember(getInstance().jda.getSelfUser()).getColor();
    }
}

class ListenerClass implements LogListener {

    JDA jda(){return Ticket.getInstance().getJda();}
    LogWindow logger = Ticket.getInstance().logger;

    @Override
    public void onLogInput(String input) {
            logger.log("All bot interactions will now be in Discord. Nothing further to do here...");
            logger.log("If you wish to get help using the bot, type \"" + Ticket.getInstance().prefix + "help\" in the discord chat.");
            logger.log("If you wish to stop the bot, simply close the window.");
    }

}
