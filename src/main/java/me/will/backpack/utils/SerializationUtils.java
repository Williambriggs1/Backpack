package me.will.backpack.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import static me.will.backpack.utils.InventoryUtils.readInventory;
import static me.will.backpack.utils.InventoryUtils.writeInventory;

public class SerializationUtils {


    public static byte[] serializeInventory(@NotNull Inventory inventory){

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                writeInventory(inventory, oos);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static void deserializeInventory(@NotNull Inventory inventory, byte[] bytes, Player player) {

        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes); ObjectInputStream ois = new ObjectInputStream(bis)) {
            readInventory(inventory, ois, player);
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e);
        }
    }
}


