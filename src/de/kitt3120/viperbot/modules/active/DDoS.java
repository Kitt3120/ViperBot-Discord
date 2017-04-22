package de.kitt3120.viperbot.modules.active;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/**
 * Created by kitt3120 on 22.04.2017.
 */
public class DDoS extends Module {

    public DDoS() {
        super("DDoS", "Launches a DDoS-Attack", false, false, false);
    }

    @Override
    public boolean onMessage(final User user, Message message, final MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;

        MessageBuilder builder = new MessageBuilder(channel);
        if (!Core.ddosManager.hasAccess(user)) {
            builder.append("You dont have access to DDoS").send();
            return true;
        }
        if (args.length == 0) {
            builder.append("!DDoS <ip> <port (80)> <time>").send();
            return true;
        } else {
            String arg = args[0];
            if (arg.equalsIgnoreCase("stop")) {
                if (Core.ddosManager.hasDDoSAttackRunning(user)) {
                    Core.ddosManager.stopAttack(user);
                    //Will run the onInterrupt method, so dont give a message here (See DDoSAttack class in objects package);
                    return true;
                } else {
                    builder.append("There is no DDoS-Attack running by you").send();
                    return true;
                }
            } else {
                if (args.length < 3) {
                    builder.append("!DDoS <ip> <port (80)> <time>").send();
                    return true;
                }
                String ip = null;
                int port = 80, time = 0;
                try {
                    ip = args[0];
                } catch (Exception e) {
                    builder.append(args[0] + " is not a valid input for argument <ip>");
                }
                try {
                    port = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    builder.append(args[1] + " is not a valid input for argument <port>");
                }
                try {
                    time = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    builder.append(args[2] + " is not a valid input for argument <time>");
                }
                if (builder.getCurrentMessage().length() > 0) {
                    builder.send();
                    return true;
                }
                if (ip.equalsIgnoreCase("localhost") || ip.equalsIgnoreCase("127.0.0.1") || ip.equalsIgnoreCase("93.158.200.100")) {
                    builder.append("Nah! Dont wanna DDoS that ;)").send();
                    return true;
                }
                if (time > 60) {
                    time = 60;
                    builder.append("Time limit is 60 seconds. Changed time to 60");
                }
                Core.ddosManager.startAttack(user, ip, port, time, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            user.openPrivateChannel().complete(true);
                        } catch (RateLimitedException e) {
                            e.printStackTrace();
                        }
                        new MessageBuilder(user.getPrivateChannel()).append("Your DDoS-Attack is finished").send();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        try {
                            user.openPrivateChannel().complete(true);
                        } catch (RateLimitedException e) {
                            e.printStackTrace();
                        }
                        new MessageBuilder(user.getPrivateChannel()).append("Your DDoS-Attack has been interrupted!").send();
                    }
                });
                builder.append("DDoS-Attack started").send();
                if (!(channel instanceof PrivateChannel)) {
                    try {
                        user.openPrivateChannel().complete(true);
                    } catch (RateLimitedException e) {
                        e.printStackTrace();
                    }
                    new MessageBuilder(user.getPrivateChannel()).append("Your DDoS-Attack on " + ip + ":" + port + " for " + time + " seconds has started").send();
                }
                return true;
            }
        }
    }

    @Override
    public void fireEvent(Event event) {

    }
}
