package tech.gesp.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.io.Serializable;
import java.util.HashMap;

@AllArgsConstructor
@Getter
public enum Setting {

    CHECK_FOR_UPDATES(ConfigurationFile.CONFIG, "check_for_updates"),
    ALLOW_OPENING_SHULKER_IN_HOTBAR(ConfigurationFile.CONFIG, "allow_opening_shulker_in_hotbar");

    public static final HashMap<Setting, String> CONFIGURATION_VALUES = new HashMap<>();

    private ConfigurationFile ymlFile;
    private String valuePath;

    public String value() {
        return CONFIGURATION_VALUES.get(this);
    }

    public String colorTranslatedValue() {
        return ChatColor.translateAlternateColorCodes('&', CONFIGURATION_VALUES.get(this));
    }

    public <T extends Serializable> T getValueType(T type) {
        return (T)CONFIGURATION_VALUES.get(this);
    }

    public ConfigurationSection getConfiguration() {
        return ymlFile.value().getConfigurationSection(valuePath);
    }

    public int intValue() {
        return Integer.parseInt(CONFIGURATION_VALUES.get(this));
    }

    public double doubleValue() {
        return Double.parseDouble(CONFIGURATION_VALUES.get(this));
    }

    public boolean booleanValue() {
        return Boolean.parseBoolean(CONFIGURATION_VALUES.get(this));
    }
}
