package com.optimus.practice;

import com.optimus.practice.Config.Config;
import com.optimus.practice.listeners.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static String VERSION = "v0.1.0";
    private static Main main;

    @Override
    public void onEnable(){
        main = this;
        registerCommands();
        registerListeners();
        Config.init();
        Bukkit.broadcastMessage(ChatColor.GREEN  + "[PRACTICE] " + VERSION + " Enabled!");
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new EventListener(), this);
    }

    private void registerCommands(){

    }

    public static Main getInstance(){
        return main;
    }

}
