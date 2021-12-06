package com.optimus.practice.config;

import com.optimus.practice.Practice;
import com.optimus.practice.player.PlayerConfiguration;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerConfig {

    private static Practice practice = Practice.getInstance();

    public static void createPlayerFile(Player player){
        File folder = new File(practice.getDataFolder() + File.separator + "Players");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File playerFile = new File(practice.getDataFolder() + File.separator + "Players" + File.separator + Bukkit.getPlayer(player.getName()).getUniqueId() + ".yml");
        if (!playerFile.exists()) {
            try{
                playerFile.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

                config.set("Wins", 0);
                config.set("Losses", 0);
                config.set("Points", 0);

                config.save(playerFile);

            }catch (IOException e){
                e.printStackTrace();
            }
        }
        PracticePlayerManager.getPlayer(player).setConfig(new PlayerConfiguration(playerFile, YamlConfiguration.loadConfiguration(playerFile)));
    }

}
