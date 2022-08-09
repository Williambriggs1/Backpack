package me.will.backpack.listeners;

import me.will.backpack.Backpack;
import me.will.backpack.utils.SerializationUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class CloseInventory implements Listener {

    @EventHandler
    public void onInventoryClose(@NotNull InventoryCloseEvent event) {
        System.out.println(Objects.requireNonNull(event.getInventory().getHolder()));

        if (event.getInventory().getType().equals(InventoryType.DISPENSER) && event.getView().title().equals(Component.text("Backpack"))) {
            if (event.getPlayer().getEquipment().getItemInMainHand().getType() != Material.AIR && event.getPlayer().getEquipment().getItemInMainHand().hasItemMeta()) {

                if (event.getPlayer().getEquipment().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Backpack.getPlugin(), "backpack_id"), PersistentDataType.BYTE_ARRAY)) {

                    byte[] serializedData = SerializationUtils.serializeInventory(event.getInventory());

                    ItemStack heldItem = event.getPlayer().getEquipment().getItemInMainHand();
                    ItemMeta itemMeta = heldItem.getItemMeta();
                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();


                    assert serializedData != null;
                    container.set(new NamespacedKey(Backpack.getPlugin(), "backpack_id"), PersistentDataType.BYTE_ARRAY, serializedData);
                    heldItem.setItemMeta(itemMeta);


                }
            }
        }
    }
}
