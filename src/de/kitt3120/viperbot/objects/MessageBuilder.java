package de.kitt3120.viperbot.objects;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.requests.RestAction;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class MessageBuilder {

    private MessageChannel channel;
    private StringBuilder builder;
    private boolean autoClear;

    public MessageBuilder(MessageChannel channel) {
        this(channel, true);
    }

    public MessageBuilder(MessageChannel channel, boolean autoClear) {
        this.channel = channel;
        this.autoClear = autoClear;
        this.builder = new StringBuilder();
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

    public MessageBuilder clear() {
        builder = new StringBuilder();
        return this;
    }

    public String getCurrentMessage() {
        return builder.toString();
    }

    public MessageBuilder send() {
        channel.sendMessage(builder.toString()).queue();
        if (autoClear) clear();
        return this;
    }

    public Message sendAndGetMessage() {
        RestAction<Message> action = channel.sendMessage(builder.toString());
        action.queue();
        if (autoClear) clear();
        return action.complete();
    }
}
