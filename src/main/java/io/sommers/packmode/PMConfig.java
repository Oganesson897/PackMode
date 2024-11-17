package io.sommers.packmode;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;

@Config(modid = PackMode.MOD_ID)
public class PMConfig {

    private static Property packMode;
    private static Property acceptedModes;
    private static Property disableTipScreen;

    private static Configuration configuration;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init(File suggestedConfigurationFile) {
        if (!suggestedConfigurationFile.exists()) {
            suggestedConfigurationFile.mkdir();
        }

        configuration = new Configuration(suggestedConfigurationFile);
        configuration.load();
        packMode = configuration.get("general", "packMode", "normal");
        acceptedModes = configuration.get("general", "acceptedModes", new String[] {"normal", "expert"});
        disableTipScreen = configuration.get("general", "disableTipScreen", false);
        configuration.save();
    }

    public static void save() {
        configuration.save();
    }

    public static void setPackMode(String newPackMode) {
        packMode.set(newPackMode);
        configuration.save();
    }

    public static void setDisableTipScreen(boolean newValue) {
        disableTipScreen.set(newValue);
        configuration.save();
    }

    public static String getPackMode() {
        return packMode.getString();
    }

    public static String[] getAcceptedModes() {
        return acceptedModes.getStringList();
    }

    public static boolean disabledTipScreen() {
        return disableTipScreen.getBoolean();
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}
