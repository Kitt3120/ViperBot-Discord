package de.kitt3120.viperbot.modules.active;

import de.kitt3120.viperbot.objects.MessageBuilder;
import de.kitt3120.viperbot.objects.Module;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.Event;

import java.io.*;

/**
 * Created by yvesdaniel on 21/04/17.
 */
public class Jcompile extends Module {
    public Jcompile() {
        super("jcompile", "java compiler", false, false, false);
    }

    @Override
    public boolean onMessage(final User user, Message message, final MessageChannel channel, boolean isPrivate, String[] args) {

        if (!super.onMessage(user, message, channel, isPrivate, args)) return false;


        final String code = "public class "+user.getId()+" {"+(message.getContent()
                .replace("!scan ", "")
                .replace("```", ""))+"}";
        final String name = code.split(" ")[2];


        Thread thread = new Thread(new Runnable() {
            Process p;

            public void run() {
                try {

                    BufferedWriter bw = new BufferedWriter(new FileWriter("./" + name + ".java"));
                    bw.write(code);
                    bw.flush();
                    bw.close();

                    p = Runtime.getRuntime().exec("javac ./" + user.getId() + ".java&&java " + user.getId());
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    MessageBuilder msg = new MessageBuilder(channel).append("```\n");
                    String line;
                    while ((line = input.readLine()) != null) {
                        msg = msg.append(line + "\n");
                    }

                    msg.append("```");
                    msg.send();

                    input.close();
                    File x = new File("./" + user.getId() + ".java");
                    x.delete();
                    x= new File("./" + user.getId() + ".class");
                    x.delete();
                } catch (Exception e) {
                    return;
                }
            }
        });
        thread.run();
        return true;
    }

    @Override
    public void fireEvent(Event event) {
        return;
    }
}
