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
import proj.bot.ticket.config.ServerConfig;
import proj.bot.ticket.config.TicketConfig;
import proj.bot.ticket.events.TicketListener;
import proj.bot.ticket.supports.SupportType;

public class TicketBot {

    public static String filePath;
    
    @Getter
    private static TicketBot instance;
    @Getter
    private JDA jda;
    public LogWindow logger;
    
    public String prefix;

    public boolean initiated = false;

    public static void main(String[] args) throws LoginException, InterruptedException {
        if(args.length > 0 && args[0].equalsIgnoreCase("nogui")) {
            new TicketBot().enable();
        } else {
            new TicketBot().enableGUI();
        }
    }
    
    public void enable() {
        instance = this;
        init();
    }
    
    public void enableGUI() {
        instance = this;
        logger = new LogWindow("TicketBot", 800, 400);
        logger.addLogListener(new ListenerClass());
        init();
    }
    
    @SuppressWarnings("deprecation")
    public void init() {
        System.out.println("Starting Bot, please wait...");
        
        try { filePath = new File(TicketBot.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().toString()).getParent(); } catch(Exception e) {e.printStackTrace(); System.out.println("Error getting file path, shutting down."); return;}
        
        TicketConfig config = new TicketConfig(filePath, "TicketConfig.yml");
        prefix = config.getPrefixIdentifier();
        
        try {
            getInstance().jda = new JDABuilder(AccountType.BOT).setToken(config.getToken()).buildBlocking();
        } catch (Exception e) {
            System.out.println("There was a problem starting the bot. Please try again.");
            return;
        }
        getInstance().initiated = true;
        System.out.println("Bot started!");
        
        getInstance().jda.getPresence().setGame(Game.playing("Type: " + prefix + "help"));
        getInstance().jda.addEventListener(new TicketListener());
        
        getInstance().jda.getGuilds().stream().forEach(guild -> {
            guild.getController().addSingleRoleToMember(guild.getMember(getInstance().getJda().getSelfUser()), SupportType.getSupportRole(guild)).queue();
            ServerConfig sconfig = new ServerConfig(guild.getId());
            for(SupportType type : SupportType.values()) {
                if(sconfig.getSupportType(type)) {
                    type.enable(guild);
                }
            }
        });
    }

    public static Color defColor(Guild guild) {
        return guild.getMember(getInstance().jda.getSelfUser()).getColor();
    }
}

class ListenerClass implements LogListener {

    JDA jda(){return TicketBot.getInstance().getJda();}
    LogWindow logger = TicketBot.getInstance().logger;

    @Override
    public void onLogInput(String input) {
            logger.log("All bot interactions will now be in Discord. Nothing further to do here...");
            logger.log("If you wish to get help using the bot, type \"" + TicketBot.getInstance().prefix + "help\" in the discord chat.");
            logger.log("If you wish to stop the bot, simply close the window.");
    }

}
