package tech.gesp.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tech.gesp.EnhancedShulkerBoxPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@AllArgsConstructor
public enum ConfigurationFile {

    CONFIG("config.yml");

    @Getter
    private String path;

    public FileConfiguration value() {
        return YamlConfiguration.loadConfiguration(new File(EnhancedShulkerBoxPlugin.getInstance().getDataFolder(), path));
    }

    /**
     * Copies yml configuration files from resources to the plugin server folder when they exist,
     * otherwise it creates an empty configuration file
     */
    public static void createDefaults() throws IOException {
        for (ConfigurationFile configuration : ConfigurationFile.values()) {
            File customConfigFile = new File(EnhancedShulkerBoxPlugin.getInstance().getDataFolder(), configuration.path);
            if (!customConfigFile.exists()) {
                if (EnhancedShulkerBoxPlugin.getInstance().getResource(configuration.path) != null) {
                    EnhancedShulkerBoxPlugin.getInstance().saveResource(configuration.path, false);
                    EnhancedShulkerBoxPlugin.getInstance().getLogger().log(Level.INFO, "File: " + configuration.path + " did not yet exist, so copied resource template to server!");
                } else {
                    configuration.value().save(customConfigFile);
                    EnhancedShulkerBoxPlugin.getInstance().getLogger().log(Level.INFO, "File: " + configuration.path + " did not yet exist, so created an empty one!");
                }
            }
        }
    }

}
