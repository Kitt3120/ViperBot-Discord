package de.kitt3120.viperbot.objects;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class AIBot {

    private static ChatterBotFactory factory;
    private static HashMap<MessageChannel, ChatterBot> bots = new HashMap<MessageChannel, ChatterBot>();
    private static HashMap<MessageChannel, ChatterBotSession> sessions = new HashMap<MessageChannel, ChatterBotSession>();

    public static void setup() {
        factory = new ChatterBotFactory();
    }

    public static void handle(MessageChannel channel, String msg) {
        final MessageBuilder builder = new MessageBuilder(channel);
        ChatterBotSession session;
        if (msg.equalsIgnoreCase("Clear")) {
            if (msg.equalsIgnoreCase("Clear")) {
                clearChat(channel);
                return;
            }
        }
        if (msg.toLowerCase().startsWith("setai")) {
            try {
                String id = msg.split(" ")[1];
                setAi(channel, id);
            } catch (Exception e) {
                builder.append("Something went wrong. Please do it like this: SetAI <id>").send();
            }
            return;
        }
        if (!sessions.containsKey(channel)) {
            builder.append("No active session found, creating new one...").send().clear();
            if (!bots.containsKey(channel)) {
                builder.append(
                        "No AI is set for this chat. Creating new Bot with default AI.\n" +
                                "For more AI's please visit https://pandorabots.com/botmaster/en/mostactive\n" +
                                "You can set the AI by saying SetAI <id>").send().clear();
                setAi(channel, "a49104941e378378");
            }
            session = bots.get(channel).createSession();
            sessions.put(channel, session);
        }
        session = sessions.get(channel);
        builder.append(think(session, msg));
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                builder.send();
            }
        }, new Random().nextInt(3), TimeUnit.SECONDS);
    }

    private static String think(ChatterBotSession session, String msg) {
        try {
            String think = session.think(msg);
            think = think.replaceAll("Ã¤", "ä");
            think = think.replaceAll("Ã¶", "ö");
            think = think.replaceAll("Ã¼", "ü");
            think = think.replaceAll("ÃŸ", "ß");
            think = think.replaceAll("ALICE", "Viper");
            think = think.replaceAll("R.I.V.K.A", "Viper");
            think = think.replaceAll("Wallace", "Viper_");
            think = think.replaceAll("Programmer", "Programmer, Viper_");
            think = think.replaceAll("programmer", "programmer, Viper_");
            think = think.replaceAll("Moti", " Viper_");
            think = think.replaceAll("moti", " Viper_");
            think = think.replaceAll("<br>", "\n");
            return think;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    private static void clearChat(MessageChannel channel) {
        sessions.remove(channel);
        new MessageBuilder(channel).append("Session cleared").send();
    }

    private static void setAi(MessageChannel channel, String id) {
        if (sessions.containsKey(channel)) {
            clearChat(channel);
        }
        MessageBuilder builder = new MessageBuilder(channel);
        builder.append("Creating new bot with AI-ID " + id).send();
        builder.clear();
        try {
            bots.put(channel, factory.create(ChatterBotType.PANDORABOTS, id));
            sessions.put(channel, bots.get(channel).createSession());
            builder.append("Session created");
        } catch (Exception e) {
            builder.append("Error: " + e.getMessage());
        }
        builder.send();
    }

}
