package me.duncanruns.worldresetmod;

import net.fabricmc.api.ModInitializer;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Scanner;

public class WorldResetMod implements ModInitializer {
    public static final String MOD_ID = "worldresetmod";
    public static final String MOD_NAME = "World Reset Mod";
    public static Logger LOGGER = LogManager.getLogger();

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        File worldResetFile = new File("disableWorldReset");
        if(!worldResetFile.exists()) {
            deleteWorld(getWorld());
        } else {
            log(Level.INFO,"Resets are currently disabled.");
        }
    }

    // Gets the world specified by level-name in the current directory's server.properties
    public String getWorld() {
        try {
            Scanner propertiesScanner = new Scanner(new File("server.properties"));
            boolean foundWorldName = false;
            String worldName = null;
            String nextLine;
            String[] splits;
            while (!foundWorldName) {
                if (propertiesScanner.hasNext()) {
                    nextLine = propertiesScanner.nextLine();
                    splits = nextLine.split("=");
                    if (splits[0].equals("level-name")) {
                        worldName = splits[1];
                        foundWorldName = true;
                    }
                } else {
                    foundWorldName = true;
                }
            }

            if (worldName == null) {
                log(Level.INFO, "World name not found in server.properties");
            }
            return worldName;

        } catch (Exception e) {
            log(Level.INFO, "Failed to read server.properties.");
            return null;
        }
    }

    // Deletes the world specified worldName, which is usually obtained from getWorld()
    public void deleteWorld(String worldName) {
        if (worldName != null) {
            try {
                File file = new File(worldName);
                if (file.exists()) {
                    FileUtils.deleteDirectory(file);
                    log(Level.INFO, "World \"" + worldName + "\" folder deleted.");
                } else {
                    log(Level.INFO, "World folder \"" + worldName + "\" does not exist.");

                }
            } catch (Exception e) {
                log(Level.INFO, "World folder \"" + worldName + "\" does not exist.");
            }
        }
    }
}