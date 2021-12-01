package com.optimus.practice.Config;

import com.optimus.practice.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Config {

    private static Main main = Main.getInstance();
    private static FileConfiguration config;
    private static File file;

    public static String LOBBYLOCATION = "LobbyLocation";
    public static String LOBBYITEMS = "LobbyItems";

    public static void init(){
        createConfig();

    }

    public static Object getValue(String path){
        return config.get(path);
    }

    public static void setValue(String path, Object item){
        try {
            config.set(path, item);
            config.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void createConfig(){
        File file = new File(main.getDataFolder() + File.separator + "config.yml");
        if (!file.exists()) {
            try{
                file.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                config.set("LobbyLocation", new Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0));
                config.set("LobbyItems", new ArrayList<ItemStack>());

                config.save(file);

                Config.config = config;
                Config.file = file;
            }catch (IOException e){
                e.printStackTrace();
            };
        }
    }

}
