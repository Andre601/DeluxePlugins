package de.fusion.deluxeplugins;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import de.fusion.deluxeplugins.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeluxePlugins extends JavaPlugin {

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static DeluxePlugins instance;
    private static ConfigManager configuration;
    private boolean running;
    private static TaskChainFactory taskChainFactory;

    @Override
    public void onLoad() {
        instance = this;
        running = true;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        taskChainFactory = BukkitTaskChainFactory.create(this);

        File config = new File(getDataFolder(), "config.yml");
        if (!getDataFolder().exists()) getDataFolder().mkdir();
        if (!config.exists())
            saveResource("config.yml", true);
        configuration = new ConfigManager(config).build();
        log("Loaded DeluxePlugins by FusionCoding");


        PluginManager pm = Bukkit.getPluginManager();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        running = false;
    }


    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(getPrefix() + message);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public static DeluxePlugins getInstance() {
        return instance;
    }

    public static ConfigManager getConfiguration() {
        return configuration;
    }

    public static String getPrefix() {
        return getConfiguration().getPath("General.Prefix").getString();
    }

    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }

    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }


}
