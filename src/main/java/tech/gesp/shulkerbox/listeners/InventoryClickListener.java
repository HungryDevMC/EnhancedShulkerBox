package tech.gesp.shulkerbox.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tech.gesp.shulkerbox.EnhancedShulkerBox;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        ItemStack cursorItem = e.getCursor();
        if (currentItem == null) return;
        if (cursorItem == null || cursorItem.getType().equals(Material.AIR)) return;

        EnhancedShulkerBox.getShulkerFromItem(currentItem).ifPresent(shulkerBox -> {
            boolean hasAdded = EnhancedShulkerBox.addItemToShulkerBox(cursorItem, shulkerBox);
            e.setCancelled(hasAdded);
        });
    }

}
