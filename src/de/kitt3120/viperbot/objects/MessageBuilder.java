package de.kitt3120.viperbot.objects;

import de.kitt3120.viperbot.Core;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.MessageChannel;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class MessageBuilder {

    private MessageChannel channel;
    private StringBuilder builder;

    public MessageBuilder(MessageChannel channel) {
        this.channel = channel;
        builder = new StringBuilder();
    }

    public MessageBuilder append(String text) {
        builder.append(text);
        return this;
    }

    public MessageBuilder replace(String from, String to) {
        String text = builder.toString();
        text = text.replace(from, to);
        builder = new StringBuilder();
        builder.append(text);
        return this;
    }

    public MessageBuilder replaceFirst(String from, String to) {
        String text = builder.toString();
        text = text.replaceFirst(from, to);
        builder = new StringBuilder();
        builder.append(text);
        return this;
    }

    public MessageBuilder appendEmote(String name) {
        try {
            Emote emote = Core.jda.getEmotesByName(name, true).get(0);
            if (!builder.toString().endsWith(" ")) append(" ");
            append(":" + emote.getName() + ": ");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No emote called " + name + " found");
        }
        return this;
    }

    public MessageBuilder clear() {
        builder = new StringBuilder();
        return this;
    }

    public String getCurrentMessage() {
        return builder.toString();
    }

    public MessageBuilder send() {
        channel.sendMessage(builder.toString()).queue();
        return this;
    }
}
