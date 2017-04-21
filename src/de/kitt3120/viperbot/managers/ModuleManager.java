package de.kitt3120.viperbot.managers;

import de.kitt3120.viperbot.modules.*;
import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

import java.util.ArrayList;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class ModuleManager {

    private ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<Module>();

        //TODO: Register modules
        register(new Admin());
        register(new Stop());
        register(new Flip());
        register(new Test());
        register(new WannaFuck());
        register(new Jcompile());
        register(new Spam());
    }

    private void register(Module module) {
        modules.add(module);
        System.out.println("Module " + module.getName() + " registered");
    }

    public void handle(User user, Message message, MessageChannel channel, boolean isPrivate) {
        if (message.getContent().startsWith("!")) {
            String content = message.getContent().replaceFirst("!", "");
            if (content.length() == 0) {
                new MessageBuilder(channel).append("!<command>").send();
                return;
            }
            String moduleName = content.split(" ")[0];
            Module foundModule = null;
            for (Module module : modules) {
                if (module.getName().equalsIgnoreCase(moduleName)) foundModule = module;
            }
            if (foundModule == null) {
                new MessageBuilder(channel).append("No command named " + moduleName + " found").send();
                return;
            }
            content = content.replaceFirst(moduleName, "").trim();
            String[] args;
            if (content.length() == 0) {
                args = new String[]{};
            } else {
                args = content.split(" ");
            }
            foundModule.onMessage(user, message, channel, isPrivate, args);
        }
    }

    public void fireEvent(Event event) {
        for (Module module : modules) {
            module.fireEvent(event);
        }
    }
}
