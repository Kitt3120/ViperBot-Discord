package de.kitt3120.viperbot;

import de.kitt3120.viperbot.listeners.BotListener;
import de.kitt3120.viperbot.managers.AdminManager;
import de.kitt3120.viperbot.managers.FileManager;
import de.kitt3120.viperbot.managers.ModuleManager;
import de.kitt3120.viperbot.objects.AIBot;
import de.kitt3120.viperbot.utils.Auth;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import java.io.IOException;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class Core {

    public static Core instance;

    public static FileManager fileManager;
    public static AdminManager adminManager;
    public static ModuleManager moduleManager;

    public static JDA jda;

    public Core() {
        jda.getPresence().setPresence(OnlineStatus.ONLINE, new Game() {
            @Override
            public String getName() {
                return "Closed-Beta";
            }

            @Override
            public String getUrl() {
                return "https://github.com/Kitt3120";
            }

            @Override
            public GameType getType() {
                return GameType.DEFAULT;
            }
        }, false);
    }

    public static void stop() {
        try {
            adminManager.save();
            System.out.println("AdminManager: saved");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("AdminManager: Error - " + e.getMessage());
        }

        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setToken(Auth.TOKEN).buildBlocking();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not start the Bot: " + e.getMessage());
            System.exit(0);
        }

        fileManager = new FileManager();
        adminManager = new AdminManager();
        moduleManager = new ModuleManager();

        AIBot.setup();

        instance = new Core();
    }
}
