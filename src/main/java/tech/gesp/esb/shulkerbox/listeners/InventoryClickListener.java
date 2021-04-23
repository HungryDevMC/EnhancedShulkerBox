package tech.gesp.esb.shulkerbox.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tech.gesp.esb.shulkerbox.EnhancedShulkerBox;
import tech.gesp.esb.shulkerbox.exceptions.ShulkerBoxNotFoundException;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        ItemStack cursorItem = e.getCursor();
        if (currentItem == null || !currentItem.getType().equals(Material.SHULKER_BOX)) return;
        if (cursorItem == null || cursorItem.getType().equals(Material.AIR)) return;

        try {
            int amountLeft = EnhancedShulkerBox.addItemToShulkerBox(cursorItem, currentItem);
            cursorItem.setAmount(amountLeft);
            e.setCancelled(true);
        } catch (ShulkerBoxNotFoundException shulkerBoxNotFoundException) {
            shulkerBoxNotFoundException.printStackTrace();
        }
    }
}
