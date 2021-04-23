package tech.gesp.esb.shulkerbox.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tech.gesp.esb.shulkerbox.EnhancedShulkerBox;

import java.util.HashSet;
import java.util.UUID;

public class HotBarClickListener implements Listener {

    private static final HashSet<UUID> PLAYERS_EDITING_SHULKERS = new HashSet<>();

    @EventHandler
    public void onClickHotbar(PlayerInteractEvent e) {
        if (!e.getHand().equals(EquipmentSlot.HAND)) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        ItemStack itemInHand = e.getItem();
        if (itemInHand == null) return;
        if (!itemInHand.getType().equals(Material.SHULKER_BOX)) return;
        if (e.getPlayer().isSneaking()) return;

        EnhancedShulkerBox.getShulkerFromItem(itemInHand).ifPresent(shulkerBox -> {
            PLAYERS_EDITING_SHULKERS.add(e.getPlayer().getUniqueId());
            Inventory shulkerInventory = Bukkit.createInventory(null, 27, itemInHand.getItemMeta() == null ? "§6§lShulker Box" : "§6§l" + itemInHand.getItemMeta().getDisplayName());
            shulkerInventory.setContents(shulkerBox.getInventory().getContents());
            e.getPlayer().openInventory(shulkerInventory);
            e.setCancelled(true);
        });
    }

    @EventHandler
    public void onShulkerInventoryClose(InventoryCloseEvent e) {
        ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();
        if (itemInHand == null) return;
        if (!itemInHand.getType().equals(Material.SHULKER_BOX)) return;
        if (!PLAYERS_EDITING_SHULKERS.contains(e.getPlayer().getUniqueId())) return;

        Inventory shulkerInventory = e.getPlayer().getOpenInventory().getTopInventory();
        EnhancedShulkerBox.getShulkerFromItem(itemInHand).ifPresent(shulkerBox -> {
            PLAYERS_EDITING_SHULKERS.remove(e.getPlayer().getUniqueId());
            shulkerBox.getInventory().setContents(shulkerInventory.getContents());
            EnhancedShulkerBox.updateShulker(itemInHand, shulkerBox);
        });
    }

}
