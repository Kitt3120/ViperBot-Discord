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
public class WannaFuck extends Module {

    public WannaFuck() {
        super("WannaFuck?", "Ye", false, false, false);
    }
    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if(!super.onMessage(user, message, channel, isPrivate, args)) return false;

        new MessageBuilder(channel).append("```MarkDown\n#Ye <3\n```").send();
        return true;
    }

    @Override
    public void fireEvent(Event event) {

    }
}
