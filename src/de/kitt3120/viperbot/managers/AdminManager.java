package de.kitt3120.viperbot.managers;

import de.kitt3120.viperbot.Core;
import de.kitt3120.viperbot.utils.Auth;
import net.dv8tion.jda.core.entities.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class AdminManager {

    private ArrayList<String> admins;
    private File adminFile;

    public AdminManager() {
        admins = new ArrayList<String>();
        adminFile = Core.fileManager.get("Admins.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(adminFile));
            String line;
            while ((line = reader.readLine()) != null) {
                admins.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No admin file found. Creating default...");
            try {
                reset();
                System.out.println("Done");
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("Error while resetting");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading admin file. Resetting to default...");
            try {
                reset();
                System.out.println("Done");
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("Error while resetting");
            }
        }
    }

    public void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(adminFile));
        for (String id : admins) {
            writer.write(id);
            writer.newLine();
        }
        writer.close();
    }

    public void reset() throws IOException {
        admins.clear();
        admins.add(Auth.DEV_ID);
        save();
    }

    public boolean toggleAdmin(User user) throws IOException {
        if (admins.contains(user.getId())) {
            admins.remove(user.getId());
        } else {
            admins.add(user.getId());
        }
        System.out.println(user.getName() + " admin set to " + isAdmin(user));
        save();
        return admins.contains(user.getId());
    }

    public boolean isAdmin(User user) {
        return admins.contains(user.getId());
    }

    public ArrayList<String> getAdmins() {
        return admins;
    }
}
