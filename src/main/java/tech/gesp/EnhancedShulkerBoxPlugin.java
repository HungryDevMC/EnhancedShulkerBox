package tech.gesp;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tech.gesp.shulkerbox.listeners.InventoryClickListener;

public class EnhancedShulkerBoxPlugin extends JavaPlugin {

    @Getter
    private static EnhancedShulkerBoxPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);

        // TODO ADD UPDATE CHECKER
    }
}
