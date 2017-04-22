package de.kitt3120.viperbot.modules.active;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

import java.util.HashMap;
import java.util.Random;

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
            isFlipping.put(channel, true);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageBuilder builder = new MessageBuilder(channel).append(user.getName() + " flips a coin...").send();
                    try {
                        Thread.sleep((new Random().nextInt(4) + 1) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (new Random().nextBoolean()) {
                        builder.append("HEADS!").send();
                    } else {
                        builder.append("TAILS!").send();
                    }
                    isFlipping.put(channel, false);
                }
            }).start();
        }
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        return;
    }
}
