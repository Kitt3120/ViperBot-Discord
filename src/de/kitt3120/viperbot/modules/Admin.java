package de.kitt3120.viperbot.modules;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.io.IOException;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class Admin extends Module {

    public Admin() {
        super("Admin", "Manages admins", true, true, true);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;

        if (args.length == 0) {
            new MessageBuilder(channel).append(
                    "!admin toggle\n" +
                            "!admin check\n" +
                            "!admin list").send();
        } else {
            String arg = args[0];
            if (arg.equalsIgnoreCase("list")) {
                MessageBuilder builder = new MessageBuilder(channel);
                for (String id : Core.adminManager.getAdmins()) {
                    builder.append(", " + Core.jda.getUserById(id).getName());
                }
                builder.replaceFirst(", ", "").send();
            } else if (arg.equalsIgnoreCase("check")) {
                MessageBuilder builder = new MessageBuilder(channel);
                if (message.getMentionedUsers().size() == 0) {
                    builder.append("You did not mention any users");
                } else {
                    for (User u : message.getMentionedUsers()) {
                        if (Core.adminManager.isAdmin(u)) {
                            builder.append(u.getName() + " is an admin\n");
                        } else {
                            builder.append(u.getName() + " is not an admin\n");
                        }
                    }
                }
                builder.send();
            } else if (arg.equalsIgnoreCase("toggle")) {
                MessageBuilder builder = new MessageBuilder(channel);
                if (message.getMentionedUsers().size() == 0) {
                    builder.append("You did not mention any users");
                } else {
                    for (User u : message.getMentionedUsers()) {
                        try {
                            boolean isAdmin = Core.adminManager.toggleAdmin(u);
                            builder.append(u.getName() + " admin set to " + isAdmin + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                            builder.append("There was an error saving the admins file. The admin changes may only be temporary now!! Please save with !admin save\n");
                        }
                    }
                }
                builder.send();
            } else if (arg.equalsIgnoreCase("save")) {
                MessageBuilder builder = new MessageBuilder(channel);
                try {
                    Core.adminManager.save();
                    builder.append("Saved");
                } catch (IOException e) {
                    e.printStackTrace();
                    builder.append("Error: " + e.getMessage());
                }
                builder.send();
            } else {
                new MessageBuilder(channel).append("Invalid arguments. !admin for help").send();
            }
        }
        return true;
    }
}
