package de.kitt3120.viperbot.modules.passive;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;

/**
 * Created by kitt3120 on 22.04.2017.
 */
public class Greetings extends Module {

    public Greetings() {
        super("Greetings", "Greets", true, false, false);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        if (event instanceof GuildMemberJoinEvent) {
            GuildMemberJoinEvent guildMemberJoinEvent = (GuildMemberJoinEvent) event;
            if (guildMemberJoinEvent.getGuild().getId().equalsIgnoreCase("304612512224116736")) {
                new MessageBuilder(guildMemberJoinEvent.getGuild().getTextChannelById("304612512224116736")).append("Welcome to the ViperBot-Beta, " + guildMemberJoinEvent.getMember().getUser().getName()).send();
            }
        }
    }
}
