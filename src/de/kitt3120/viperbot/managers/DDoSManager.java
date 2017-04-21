package de.kitt3120.viperbot.managers;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.objects.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kitt3120 on 21.04.2017.
 */
public class DDoSManager {

    private File usersFile;

    private HashMap<User, Date> permissions;

    public DDoSManager() {
        permissions = new HashMap<User, Date>();
        usersFile = Core.fileManager.get("DDoS/users.json");

        loadUsers();

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    checkUsers();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("An error occured in the auto-check loop of the DDoS-Manager: " + e.getMessage());
                }
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    private void loadUsers() {
        if (usersFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(usersFile));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) builder.append(line);
                JSONArray array = new JSONArray(builder.toString());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    String id = object.getString("id");
                    Date date = (Date) object.get("date");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkUsers() throws IOException {
        ArrayList<User> toRemove = new ArrayList<User>();
        for (Map.Entry<User, Date> entry : permissions.entrySet()) {
            if (Calendar.getInstance().getTime().after(entry.getValue())) {
                toRemove.add(entry.getKey());
            }
        }
        for (User user : toRemove) {
            removeUser(user);
        }

        if (toRemove.size() > 0) saveUsers();
    }

    public void saveUsers() throws IOException {
        JSONArray array = new JSONArray();
        for (Map.Entry<User, Date> entry : permissions.entrySet()) {
            JSONObject object = new JSONObject();
            object.put("id", entry.getKey().getId());
            object.put("date", entry.getValue());
            array.put(object);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(usersFile));
        writer.write(array.toString());
        writer.close();
    }

    private void removeUser(User user) {
        if (permissions.containsKey(user)) {
            new MessageBuilder(user.getPrivateChannel()).append("The time of your DDoS-Permission is over").send();
            permissions.remove(user);
        }
    }

    public void setPermissions(MessageChannel responseChannel, User user, int days) throws IOException {
        if (days == 0 && permissions.containsKey(user)) {
            removeUser(user);
            return;
        }
        boolean permanent = days > 999;
        if (permanent) days = 10;
        Calendar calendar = Calendar.getInstance();
        calendar.add(permanent ? Calendar.YEAR : Calendar.DAY_OF_MONTH, days);
        Date date = calendar.getTime();
        permissions.put(user, date);
        saveUsers();
        MessageBuilder response = new MessageBuilder(responseChannel);
        MessageBuilder privateMessage = new MessageBuilder(user.getPrivateChannel());
        if (permanent) {
            response.append(user.getName() + " now has permanent access to DDoS");
            privateMessage.append("You now have permanent access to DDoS");
        } else {
            response.append(user.getName() + " now has access to DDoS until " + date.toString());
            privateMessage.append("You now have access to DDoS until " + date.toString());
        }
        response.send();
        privateMessage.send();
    }

    public boolean hasAccess(User user) {
        return permissions.containsKey(user);
    }
}
