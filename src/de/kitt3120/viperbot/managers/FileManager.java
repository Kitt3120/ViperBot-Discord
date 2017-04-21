package de.kitt3120.viperbot.managers;

import java.io.File;

/**
 * Created by kitt3120 on 20.04.2017.
 */
public class FileManager {

    private File root;

    public FileManager() {
        root = new File(System.getenv("APPDATA"), ".viperBotDiscord");
        if (!root.exists()) root.mkdirs();
    }

    public File get(String name) {
        File file = new File(root, name);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return file;
    }

    public File getRoot() {
        return root;
    }
}
