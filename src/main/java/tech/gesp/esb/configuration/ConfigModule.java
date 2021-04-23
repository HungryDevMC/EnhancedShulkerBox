package tech.gesp.configuration;

import java.io.IOException;
import java.util.Arrays;

public class ConfigModule {

    public static void initialize() {
        try {
            ConfigurationFile.createDefaults();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        Arrays.stream(Setting.values()).forEach(
                configuration -> Setting.CONFIGURATION_VALUES.put(configuration, configuration.getYmlFile().value().getString(configuration.getValuePath())));
    }
}
