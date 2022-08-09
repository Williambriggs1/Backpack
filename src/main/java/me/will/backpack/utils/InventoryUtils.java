package me.will.backpack.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Objects;

public class InventoryUtils {

    public static void writeInventory(@NotNull Inventory inventory, @NotNull ObjectOutputStream oos) throws IOException {
        ItemStack[] contents = inventory.getContents();
        int size = contents.length;
        int sized = (int) Arrays.stream(contents).filter(Objects::nonNull).count();
        oos.writeInt(sized);
        for (int slot = 0; slot < size; slot++) {
            ItemStack item = contents[slot];
            if (item == null) {
                continue;
            }
            oos.writeInt(slot);
            byte[] itemAsBytes = item.serializeAsBytes();
            oos.writeInt(itemAsBytes.length);
            oos.write(itemAsBytes);
        }
    }


    public static void readInventory(@NotNull Inventory inventory, @NotNull ObjectInputStream ois, @NotNull Player player) throws IOException, ClassNotFoundException {
        int size = ois.readInt();
        for (int i = 0; i < size; i++) {
            int slot = ois.readInt();
            int rawSize = ois.readInt();
            byte[] rawItem = new byte[rawSize];
            if(ois.read(rawItem) == rawSize){
                ItemStack itemStack = ItemStack.deserializeBytes(rawItem);
                inventory.setItem(slot, itemStack);
            }
        }
        player.openInventory(inventory);
    }

}
