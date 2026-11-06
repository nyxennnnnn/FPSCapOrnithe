package dev.tiller.customfpscap.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/** Loads the optional FPS cap from config/custom-fps-cap.properties. */
public final class FpsCapConfig {
    private static final String KEY = "limit";
    private static final int DEFAULT_LIMIT = 600;
    private static final int LIMIT;

    static {
        LIMIT = loadLimit();
    }

    private FpsCapConfig() {
    }

    public static int getLimit() {
        return LIMIT;
    }

    private static int loadLimit() {
        File configDirectory = new File(System.getProperty("user.dir"), "config");
        File configFile = new File(configDirectory, "custom-fps-cap.properties");
        Properties properties = new Properties();

        if (!configDirectory.exists() && !configDirectory.mkdirs()) {
            return DEFAULT_LIMIT;
        }

        if (!configFile.exists()) {
            properties.setProperty(KEY, String.valueOf(DEFAULT_LIMIT));
            try (FileOutputStream output = new FileOutputStream(configFile)) {
                properties.store(output, "Custom FPS Cap. Set limit=0 to use Minecraft's normal FPS setting.");
            } catch (IOException ignored) {
                return DEFAULT_LIMIT;
            }
            return DEFAULT_LIMIT;
        }

        try (FileInputStream input = new FileInputStream(configFile)) {
            properties.load(input);
            int value = Integer.parseInt(properties.getProperty(KEY, String.valueOf(DEFAULT_LIMIT)).trim());
            return Math.max(0, value);
        } catch (IOException | NumberFormatException ignored) {
            return DEFAULT_LIMIT;
        }
    }
}
