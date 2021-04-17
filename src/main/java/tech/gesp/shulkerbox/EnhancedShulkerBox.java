package tech.gesp.shulkerbox;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnhancedShulkerBox {

    public static Optional<ShulkerBox> getShulkerFromItem(ItemStack shulkerItem) {
        if (shulkerItem == null) return Optional.empty();
        if (!shulkerItem.getType().equals(Material.SHULKER_BOX)) return Optional.empty();
        if (!(shulkerItem.getItemMeta() instanceof BlockStateMeta)) return Optional.empty();

        BlockStateMeta shulkerBoxMeta = (BlockStateMeta) shulkerItem.getItemMeta();
        if (!(shulkerBoxMeta.getBlockState() instanceof ShulkerBox)) return Optional.empty();

        ShulkerBox shulkerBox = (ShulkerBox) shulkerBoxMeta.getBlockState();
        return Optional.of(shulkerBox);
    }

    public static boolean addItemToShulkerBox(ItemStack itemToAdd, ShulkerBox shulkerBox) {
        Inventory shulkerInventory = shulkerBox.getInventory();
        List<ItemStack> itemsThatCanAddTo = new ArrayList<>();
        if (itemToAdd.getMaxStackSize() > 1) {
            itemsThatCanAddTo = Arrays.stream(shulkerInventory.getContents())
                    .filter(itemInShulker -> itemInShulker.getType().equals(itemToAdd.getType()))
                    .filter(itemInShulker -> itemInShulker.getAmount() < itemInShulker.getMaxStackSize())
                    .collect(Collectors.toList());
        }

        if (itemsThatCanAddTo.size() > 0) {
            for (ItemStack itemToAddTo : itemsThatCanAddTo) {
                shulkerInventory.removeItem(itemToAddTo);
                int freeToAdd = itemToAddTo.getMaxStackSize() - itemToAddTo.getAmount();
                int toActuallyAdd = Math.min(freeToAdd, itemToAdd.getAmount());
                itemToAdd.setAmount(itemToAdd.getAmount() - toActuallyAdd);
                if (itemToAdd.getAmount() > 0) {
                    itemToAddTo.setAmount(itemToAddTo.getMaxStackSize());
                } else {
                    itemToAddTo.setAmount(itemToAddTo.getAmount() + toActuallyAdd);
                    break;
                }
            }
        }

        if (itemToAdd.getAmount() > 0 && shulkerInventory.firstEmpty() != -1) {
            shulkerInventory.addItem(itemToAdd);
            return true;
        }

        return itemToAdd.getAmount() <= 0;
    }

}
