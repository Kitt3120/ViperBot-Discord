package de.kitt3120.viperbot.modules;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by kitt3120 on 21.04.2017.
 */
public class DDoSPermission extends Module {

    public DDoSPermission() {
        super("DDoSPermission", "Manages DDoS-Permissions", true, true, true);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;
        MessageBuilder builder = new MessageBuilder(channel);
        if (args.length > 1) {
            int days;
            try {
                days = Integer.parseInt(args[0]);
            } catch (Exception e) {
                builder.append(args[0] + " is not a valid number of days").send();
                return true;
            }

            try {
                Core.ddosManager.setPermissions(channel, message.getMentionedUsers(), days);
            } catch (IOException | RateLimitedException e) {
                e.printStackTrace();
                builder.append("There was an error: " + e.getMessage()).send();
            }
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            for (Map.Entry<User, Date> entry : Core.ddosManager.getPermissions().entrySet()) {
                builder.append(entry.getKey().getName() + " until " + entry.getValue().toString() + "\n");
            }
            if (builder.getCurrentMessage().length() > 0) {
                builder.send();
            } else {
                builder.append("No permissions set").send();
            }
            return true;
        } else {
            builder.append("!DDoSPermission list\n!DDoSPermission <days> <@User1 @User2 ...>").send();
            return true;
        }
    }

    @Override
    public void fireEvent(Event event) {

    }
}
