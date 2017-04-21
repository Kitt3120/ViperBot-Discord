package de.kitt3120.viperbot.objects;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.utils.Auth;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public abstract class Module extends ListenerAdapter {

    private String name, description;
    private boolean hidden, onlyAdmin, onlyDev;

    public Module(String name, String description, boolean hidden, boolean onlyAdmin, boolean onlyDev) {
        Core.jda.addEventListener(this);
        this.name = name;
        this.description = description;
        this.hidden = hidden;
        this.onlyAdmin = onlyAdmin;
        this.onlyDev = onlyDev;
    }

    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (onlyDev) {
            if (user.getId().equalsIgnoreCase(Auth.DEV_ID)) {
                return true;
            } else {
                disallow(channel);
                return true;
            }
        }
        if (onlyAdmin && !Core.adminManager.isAdmin(user)) {
            disallow(channel);
            return false;
        }
        return true;
    }

    public abstract void fireEvent(Event event);

    private void disallow(MessageChannel channel) {
        new MessageBuilder(channel).append("You are not allowed to do this command").send();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isOnlyAdmin() {
        return onlyAdmin;
    }

    public void setOnlyAdmin(boolean onlyAdmin) {
        this.onlyAdmin = onlyAdmin;
    }

    public boolean isOnlyDev() {
        return onlyDev;
    }

    public void setOnlyDev(boolean onlyDev) {
        this.onlyDev = onlyDev;
    }
}
