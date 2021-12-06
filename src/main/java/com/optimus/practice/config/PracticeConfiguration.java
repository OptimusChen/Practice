package com.optimus.practice.config;

import com.optimus.practice.Practice;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PracticeConfiguration {

    private Practice practice = Practice.getInstance();
    private FileConfiguration config;
    private File file;

    public static String LOBBYLOCATION = "LobbyLocation";
    public static String LOBBYITEMS = "LobbyItems";

    public PracticeConfiguration(){
        init();
    }

    private void init(){
        Bukkit.getLogger().info("test0");
        createConfig();
    }

    public Object getValue(String path){
        return config.get(path);
    }

    public void setValue(String path, Object item){
        try {
            config.set(path, item);
            config.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void createConfig(){
        Bukkit.getLogger().info("test1");
        File file = new File(practice.getDataFolder() + File.separator + "config.yml");
        Bukkit.getLogger().info("test2");
        if (!file.exists()) {
            Bukkit.getLogger().info("test3");
            try{
                Bukkit.getLogger().info("test4");
                file.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                Bukkit.getLogger().info("test5");
                config.set("LobbyLocation", new Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0));
                config.set("LobbyItems", new ArrayList<ItemStack>());

                Bukkit.getLogger().info("test6");
                config.save(file);

                Bukkit.getLogger().info("test7");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Bukkit.getLogger().info("test8");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.file = file;
        Bukkit.getLogger().info("test9");
    }

}
