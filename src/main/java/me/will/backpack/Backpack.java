package me.will.backpack;

import com.moandjiezana.toml.Toml;
import me.will.backpack.commands.ReloadCommand;
import me.will.backpack.listeners.CloseInventory;
import me.will.backpack.listeners.InventoryClick;
import me.will.backpack.listeners.PlayerInteract;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public final class Backpack extends JavaPlugin {

    private static Backpack plugin;
    public static Toml config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        registerListeners();

        config = loadConfig();


        Objects.requireNonNull(this.getCommand("Backpack")).setExecutor(new ReloadCommand());

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public Toml loadConfig() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs(); // Creating the directory as it may not exist
        }
        File file = new File(getDataFolder(), "config.toml"); // Assign a variable to the file
        if(!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource("config.toml")), file.toPath()); // Copy the file out of our jar into our plugins Data Folder
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new Toml(
                new Toml().read(getResource("config.toml"))) // We'll use our internal file as default to prevent errors
                .read(file); // Reads the file from the Data Folder of our plugin
    }

    public Toml getTomlConfig() { return config; }

    public Toml reloadPluginConfig() {
        File file = new File(getDataFolder(), "config.toml");
        return new Toml(
                new Toml().read(getResource("config.toml"))) // We'll use our internal file as default to prevent errors
                .read(file);
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteract(), this);
        pm.registerEvents(new CloseInventory(), this);
        pm.registerEvents(new InventoryClick(), this);
    }

    public static Backpack getPlugin() {
        return plugin;
    }


}
