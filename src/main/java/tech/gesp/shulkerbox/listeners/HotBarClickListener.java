package tech.gesp.shulkerbox.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import tech.gesp.shulkerbox.EnhancedShulkerBox;

public class HotBarClickListener implements Listener {

    @EventHandler
    public void onClickHotbar(PlayerInteractEvent e) {
        Bukkit.broadcastMessage("Interact event");
        if (!e.getHand().equals(EquipmentSlot.HAND)) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        Bukkit.broadcastMessage("Before checking item");

        ItemStack itemInHand = e.getItem();
        if (!itemInHand.getType().equals(Material.SHULKER_BOX)) return;
        if (e.getPlayer().isSneaking()) return;

        Bukkit.broadcastMessage("Item in hand: " + itemInHand.toString());

        EnhancedShulkerBox.getShulkerFromItem(itemInHand).ifPresent(shulkerBox -> {
            e.getPlayer().openInventory(shulkerBox.getInventory());
            e.setCancelled(true);
        });
    }

}
