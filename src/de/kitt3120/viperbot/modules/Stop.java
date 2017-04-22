package de.kitt3120.viperbot.modules;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

/**
 * Created by kitt3120 on 21.04.2017.
 */
public class Stop extends Module {

    public Stop() {
        super("Stop", "Stops (and restarts) the Bot", true, true, false);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;

        new MessageBuilder(channel).append("Restarting").send();
        Core.stop();
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        return;
    }
}
