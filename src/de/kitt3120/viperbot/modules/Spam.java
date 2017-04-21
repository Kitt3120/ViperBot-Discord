package de.kitt3120.viperbot.modules;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

/**
 * Created by yvesdaniel on 21/04/17.
 */
public class Spam extends Module {

    public Spam() {
        super("spam", "Spam", false, false, false);
    }


    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if(!super.onMessage(user, message, channel, isPrivate, args)) return false;

        final MessageBuilder builder = new MessageBuilder(channel);
        if (args.length == 0) {
            builder.append("Please use it like this: !Spam <number>").send();
            return true;
        }
        int times;
        try {
            times = Integer.parseInt(args[0]);
        } catch (Exception e) {
            builder.append(args[0] + " is not a valid number").send();
            return true;
        }
        if (times > 25) {
            times = 25;
            builder.append("Messages limited to 25").send();
        }
        final int fTimes = times;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < fTimes; i++) {
                    builder.append("```\n#SEND NUDES\n```").send();
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return true;
    }

    @Override
    public void fireEvent(Event event) {

    }
}