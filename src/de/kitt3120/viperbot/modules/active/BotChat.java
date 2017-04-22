package de.kitt3120.viperbot.modules.active;

import de.kitt3120.viperbot.objects.AIBot;
import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

/**
 * Created by kitt3120 on 22.04.2017.
 */
public class BotChat extends Module {

    public BotChat() {
        super("BotChat", "Toggles always chatting to the bot", false, false, false);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;

        MessageBuilder builder = new MessageBuilder(channel);
        if (AIBot.botChatToggled.contains(user)) {
            AIBot.botChatToggled.remove(user);
            builder.append("@" + user.getName() + " BotChat toggled off");
        } else {
            AIBot.botChatToggled.add(user);
            builder.append("@" + user.getName() + " BotChat toggled on");
        }
        builder.send();
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        return;
    }
}
