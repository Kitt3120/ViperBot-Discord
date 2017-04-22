package de.kitt3120.viperbot.modules.passive;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;

/**
 * Created by yvesdaniel on 22/04/17.
 */
public class SeeYa extends Module {

    public SeeYa() {
        super("SeeYa", "Good Bye", true, false, false);
    }

    @Override
    public boolean onMessage(User user, Message message, MessageChannel channel, boolean isPrivate, String[] args) {
        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        if (event instanceof GuildMemberLeaveEvent) {
            GuildMemberLeaveEvent guildMemberLeaveEvent = (GuildMemberLeaveEvent) event;
            if (guildMemberLeaveEvent.getGuild().getId().equalsIgnoreCase("304612512224116736")) {
                new MessageBuilder(guildMemberLeaveEvent.getGuild().getTextChannelById("304612512224116736")).append("Good bye , " + guildMemberLeaveEvent.getMember().getUser().getName()+", you little madafaka C: ").send();
            }
        }
    }
}
