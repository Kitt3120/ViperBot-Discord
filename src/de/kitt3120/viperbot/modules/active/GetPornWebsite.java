package de.kitt3120.viperbot.modules.active;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

import java.util.Random;

/**
 * Created by yvesdaniel on 25/04/17.
 */
public class GetPornWebsite extends Module {
    static String[] websites;
    public GetPornWebsite() {
        super("getPW", "Get random porn website", true, false, false);
    }
    public boolean onMessage(final User user, Message message, final MessageChannel channel, boolean isPrivate, String[] args) {

        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;
        Random e = new Random();
        new MessageBuilder(channel).append("Here you go perv: "+websites[e.nextInt(websites.length)]).send();

        return true;
    }
        @Override
    public void fireEvent(Event event) {
        return;
    }
}
