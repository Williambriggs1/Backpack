package me.will.backpack.listeners;

import me.will.backpack.Backpack;
import me.will.backpack.utils.SerializationUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteract implements Listener {

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack item = event.getItem();

//            item.getItemMeta().getCustomModelData() == 0

            if(item == null) return;
            if (item.getType() == Material.PHANTOM_MEMBRANE) {

                Player player = event.getPlayer();

                ItemMeta data = item.getItemMeta();

                if (!data.getPersistentDataContainer().has(new NamespacedKey(Backpack.getPlugin(), "backpack_id"), PersistentDataType.BYTE_ARRAY)) {

                    data.getPersistentDataContainer().set(new NamespacedKey(Backpack.getPlugin(), "backpack_id"), PersistentDataType.BYTE_ARRAY, new byte[0]);
                    item.setItemMeta(data);

                    Inventory backpack = Bukkit.createInventory(player, InventoryType.DISPENSER, Component.text("Backpack"));
                    player.openInventory(backpack);


                } else {

                    byte[] serializedData = data.getPersistentDataContainer().get(new NamespacedKey(Backpack.getPlugin(), "backpack_id"), PersistentDataType.BYTE_ARRAY);

                    Inventory backpack = Bukkit.createInventory(player, InventoryType.DISPENSER, Component.text("Backpack"));

                    SerializationUtils.deserializeInventory(backpack, serializedData, player);


                }
            }

        }
    }
}
