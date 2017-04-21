package de.kitt3120.viperbot.modules;

import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.events.Event;

/**
 * Created by kitt3120 on 21.04.2017.
 */
public class DDoSPermission extends Module {

    public DDoSPermission(String name, String description, boolean hidden, boolean onlyAdmin, boolean onlyDev) {
        super(name, description, hidden, onlyAdmin, onlyDev);
    }

    @Override
    public void fireEvent(Event event) {

    }
}
