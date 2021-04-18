package tech.gesp.shulkerbox.exceptions;

import org.bukkit.inventory.ItemStack;

public class ShulkerBoxNotFoundException extends Exception {
    public ShulkerBoxNotFoundException(ItemStack notShulkerItem) {
        super("Shulker box not found for item: " + notShulkerItem.toString());
    }
}
