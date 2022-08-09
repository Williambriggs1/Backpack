package me.will.backpack.listeners;

import me.will.backpack.Backpack;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && event.getClickedInventory() != null) {
            ItemStack item = event.getCurrentItem();
            if (player.getOpenInventory().getType().equals(InventoryType.DISPENSER) && player.getOpenInventory().getTopInventory().getHolder() != null) {
                if (item != null && item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Backpack.getPlugin(), "backpack_id"))) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
