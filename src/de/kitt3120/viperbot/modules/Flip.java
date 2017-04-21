package de.kitt3120.viperbot.modules;

import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

import java.util.HashMap;

/**
 * Created by kitt3120 on 21.04.2017.
 */
public class Flip extends Module {

    private HashMap<MessageChannel, Boolean> isFlipping;

    public Flip() {
        super("Flip", "Flips a coin", false, false, false);
        isFlipping = new HashMap<MessageChannel, Boolean>();
    }

    @Override
    public boolean onMessage(final User user, Message message, final MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;

        if (!isFlipping.containsKey(channel)) isFlipping.put(channel, false);

        if (!isFlipping.get(channel)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                }
            }).start();
        }
        return true;
    }

    @Override
    public void fireEvent(Event event) {

    }
}
