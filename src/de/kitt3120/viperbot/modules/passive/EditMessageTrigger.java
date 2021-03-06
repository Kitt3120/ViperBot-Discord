package de.kitt3120.viperbot.modules.passive;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;

/**
 * Created by yvesdaniel on 22/04/17.
 */
public class EditMessageTrigger extends Module{

    public EditMessageTrigger() {
        super("Edit Message Trigger", "Triggers when some one edit a message.", true, false, false);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        if (event instanceof GuildMessageUpdateEvent) {
            GuildMessageUpdateEvent guildMessageUpdateEvent = (GuildMessageUpdateEvent) event;
            if (guildMessageUpdateEvent.getGuild().getId().equalsIgnoreCase("304612512224116736")) {
                new MessageBuilder(guildMessageUpdateEvent.getMessage().getChannel()).append(guildMessageUpdateEvent.getMessage().getAuthor().getName()+" edited this message: ```"+guildMessageUpdateEvent.getMessage().getContent()+"```\n LULULULULULU").send();
            }
        }
    }
}
