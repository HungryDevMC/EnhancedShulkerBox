package tech.gesp.configuration;

import tech.gesp.EnhancedShulkerBoxPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class ConfigModule {

    public static void initialize() {
        try {
            ConfigurationFile.createDefaults();
        } catch (IOException ioException) {
            EnhancedShulkerBoxPlugin.getInstance().getLogger().log(Level.WARNING, ioException.getMessage());
            ioException.printStackTrace();
            EnhancedShulkerBoxPlugin.getInstance().getServer().shutdown();
        }
    }
}
