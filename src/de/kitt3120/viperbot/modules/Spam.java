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
        for(int i=0;i<Integer.parseInt(message.getContent().replace("!spam ",""));++i){
            new MessageBuilder(channel).append("```MarkDown\n#S E N D  N U D E S\n```").send();
        }
        return true;
    }

    @Override
    public void fireEvent(Event event) {

    }
}