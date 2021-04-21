package tech.gesp.shulkerbox.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HotBarClickListener implements Listener {

    @EventHandler
    public void onClickHotbar(PlayerInteractEvent e) {
        if (!e.getHand().equals(EquipmentSlot.HAND)) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || !e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

    
    }

}
