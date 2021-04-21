package tech.gesp;

import de.jeff_media.updatechecker.UpdateChecker;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tech.gesp.configuration.ConfigModule;
import tech.gesp.configuration.Setting;
import tech.gesp.shulkerbox.listeners.HotBarClickListener;
import tech.gesp.shulkerbox.listeners.InventoryClickListener;

public class EnhancedShulkerBoxPlugin extends JavaPlugin {

    private static final int RESOURCE_ID = 91446;

    @Getter
    private static EnhancedShulkerBoxPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigModule.initialize();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);

        if (Setting.ALLOW_OPENING_SHULKER_IN_HOTBAR.booleanValue()) {
            pluginManager.registerEvents(new HotBarClickListener(), this);
        }

        if (Setting.CHECK_FOR_UPDATES.booleanValue()) {
            UpdateChecker.init(this, RESOURCE_ID)
                    .setNotifyOpsOnJoin(true)
                    .checkNow();
        }
    }
}
