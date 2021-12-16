package com.optimus.practice;

import com.optimus.practice.arena.ArenaManager;
import com.optimus.practice.commands.*;
import com.optimus.practice.config.PracticeConfiguration;
import com.optimus.practice.listeners.EventListener;
import com.optimus.practice.listeners.WandListener;
import com.optimus.practice.queue.Queue;
import com.optimus.practice.queue.RankedQueue;
import com.optimus.practice.queue.UnrankedQueue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Practice extends JavaPlugin {

    public static final String VERSION = "v0.1.0";
    private static Practice practice;
    private static PracticeConfiguration config;
    private static ArenaManager arenaManager;
    private static UnrankedQueue unrankedQueue;
    private static RankedQueue rankedQueue;

    public static PracticeConfiguration getConfiguration() {
        return config;
    }

    public static Practice getInstance() {
        return practice;
    }

    public static ArenaManager getArenaManager() {
        return arenaManager;
    }

    public static Queue getRankedQueue() {
        return rankedQueue;
    }

    public static Queue getUnrankedQueue() {
        return unrankedQueue;
    }

    @Override
    public void onEnable() {
        practice = this;
        registerCommands();
        registerListeners();
        config = new PracticeConfiguration();
        arenaManager = new ArenaManager();
        arenaManager.init();
        unrankedQueue = new UnrankedQueue();
        rankedQueue = new RankedQueue();
        Bukkit.broadcastMessage(ChatColor.GREEN + "[PRACTICE] " + VERSION + " Enabled!");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
        Bukkit.getPluginManager().registerEvents(new WandListener(), this);
    }

    private void registerCommands() {
        getCommand("practiceadmin").setExecutor(new AdminCommand());
        getCommand("wand").setExecutor(new WandCommand());
        getCommand("arena").setExecutor(new ArenaCommand());
        getCommand("spawnlocation").setExecutor(new SpawnLocationCommand());
        getCommand("queue").setExecutor(new QueueCommand());
        getCommand("request").setExecutor(new RequestCommand());
    }

}
