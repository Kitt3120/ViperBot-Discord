package de.kitt3120.viperbot.modules.active;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

/**
 * Created by yvesdaniel on 21/04/17.
 */
public class Test extends Module {

    public Test() {
        super("Test", "Tests the bot", false, false, false);
    }


    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if(!super.onMessage(user, message, channel, isPrivate, args)) return false;

        new MessageBuilder(channel).append("The bot is working fine").send();
        return true;
    }

    @Override
    public void fireEvent(Event event) {

    }
}
