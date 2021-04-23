package tech.gesp.esb.shulkerbox;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import tech.gesp.esb.shulkerbox.exceptions.ShulkerBoxNotFoundException;

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

    public static void updateShulker(ItemStack originalShulkerItem, ShulkerBox shulkerBox) {
        BlockStateMeta meta = (BlockStateMeta) originalShulkerItem.getItemMeta();
        meta.setBlockState(shulkerBox);
        originalShulkerItem.setItemMeta(meta);
    }

    public static int addItemToShulkerBox(ItemStack itemToAdd, ItemStack shulkerBoxItem) throws ShulkerBoxNotFoundException {
        Optional<ShulkerBox> optionalShulkerBox = getShulkerFromItem(shulkerBoxItem);
        if(optionalShulkerBox.isEmpty()) throw new ShulkerBoxNotFoundException(shulkerBoxItem);

        ShulkerBox shulkerBox = optionalShulkerBox.get();
        Inventory shulkerInventory = shulkerBox.getInventory();
        List<ItemStack> itemsThatCanAddTo = new ArrayList<>();
        if (itemToAdd.getMaxStackSize() > 1 && shulkerInventory.getContents() != null && shulkerInventory.getContents().length > 0) {
            itemsThatCanAddTo = Arrays.stream(shulkerInventory.getContents())
                    .filter(itemInShulker -> itemInShulker != null && !itemInShulker.getType().equals(Material.AIR))
                    .filter(itemInShulker -> itemInShulker.getType().equals(itemToAdd.getType()))
                    .filter(itemInShulker -> itemInShulker.getAmount() < itemInShulker.getMaxStackSize())
                    .collect(Collectors.toList());
        }

        if (itemsThatCanAddTo.size() > 0) {
            for (ItemStack itemToAddTo : itemsThatCanAddTo) {
                int freeToAdd = itemToAddTo.getMaxStackSize() - itemToAddTo.getAmount();
                int toActuallyAdd = Math.min(freeToAdd, itemToAdd.getAmount());
                itemToAdd.setAmount(Math.max(itemToAdd.getAmount() - toActuallyAdd, 0));
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
            itemToAdd.setAmount(0);
        }

        updateShulker(shulkerBoxItem, shulkerBox);
        return itemToAdd.getAmount();
    }

}
