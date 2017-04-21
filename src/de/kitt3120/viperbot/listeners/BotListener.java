package de.kitt3120.viperbot.listeners;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.objects.AIBot;
import de.kitt3120.viperbot.objects.MessageBuilder;
import net.dv8tion.jda.client.events.relationship.FriendRequestReceivedEvent;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        User user = message.getAuthor();
        MessageChannel channel = message.getChannel();
        boolean isPrivate = message.isFromType(ChannelType.PRIVATE);

        if (!event.getAuthor().equals(Core.jda.getSelfUser())) {
            if (message.getContent().startsWith("!")) {
                Core.moduleManager.handle(user, message, channel, isPrivate);
                return;
            } else {
                if (message.getMentionedUsers().contains(Core.jda.getSelfUser())) {
                    SelfUser u = Core.jda.getSelfUser();
                    String msg = message.getContent().replace("@" + u.getName(), "");
                    msg = msg.trim();
                    AIBot.handle(channel, msg);
                    return;
                }
            }
        }
    }

    @Override
    public void onFriendRequestReceived(FriendRequestReceivedEvent event) {
        event.getFriendRequest().accept();
        new MessageBuilder(event.getUser().getPrivateChannel()).append("Thanks for adding me!").appendEmote("smile").send();
    }

    @Override
    public void onGenericEvent(Event event) {
        try {
            Core.moduleManager.fireEvent(event);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
