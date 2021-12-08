package com.optimus.practice.config;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaConfiguration;
import com.optimus.practice.arena.ArenaType;
import com.optimus.practice.arena.arenas.BoxingArena;
import com.optimus.practice.arena.arenas.PvPArena;
import com.optimus.practice.arena.arenas.SumoArena;
import com.optimus.practice.player.PlayerConfiguration;
import com.optimus.practice.player.PracticePlayerManager;
import com.optimus.practice.util.KitCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ArenaConfig {

    private static Practice practice = Practice.getInstance();

    public static void init(){
        File folder = new File(practice.getDataFolder() + File.separator + "Arenas");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        for (File file : Objects.requireNonNull(folder.listFiles())){
            FileConfiguration arenaFileConfig = YamlConfiguration.loadConfiguration(file);
            ArenaConfiguration config = new ArenaConfiguration(file);
            ArenaType type = ArenaType.valueOf(arenaFileConfig.getString("name"));
            Arena arena = null;

            switch (type) {
                case BOXING:
                    arena = new BoxingArena(config);
                    break;
                case SUMO:
                    arena = new SumoArena(config);
                    break;
                case NODEBUFF:
                    arena = new PvPArena(config, (ArrayList<ItemStack>) config.getValue("kit"), (ArrayList<ItemStack>) config.getValue("armor"));
                    break;
                default:
                    arena = new PvPArena(config, new ArrayList<>(), new ArrayList<>());
                    break;
            }

            Practice.getArenaManager().registerArena(arena, UUID.fromString(arenaFileConfig.getString("id")));
        }
    }

    public static Arena createArenaConfig(ArenaType type, ArrayList<Location> spawns, ArrayList<Location> corners){
        File folder = new File(practice.getDataFolder() + File.separator + "Arenas");
        UUID uuid = UUID.randomUUID();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File playerFile = new File(practice.getDataFolder() + File.separator + "Arenas" + File.separator + type.name() + "_" + uuid + ".yml");
        if (!playerFile.exists()) {
            try{
                playerFile.createNewFile();
                FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

                config.set("spawns", spawns);
                config.set("id", String.valueOf(uuid));
                config.set("kit", KitCreator.createKit(type));
                config.set("armor", KitCreator.createArmor(type));
                config.set("name", type.name());
                config.set("corners", corners);

                config.save(playerFile);

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        ArenaConfiguration config = new ArenaConfiguration(playerFile);
        Arena arena = null;

        switch (type) {
            case SUMO:
                arena = new SumoArena(config);
                break;
            case BOXING:
                arena = new BoxingArena(config);
                break;
            case NODEBUFF:
                arena = new PvPArena(config, (ArrayList<ItemStack>) config.getValue("kit"), (ArrayList<ItemStack>) config.getValue("kit"));
                break;
            default:
                arena = new PvPArena(config, new ArrayList<>(), new ArrayList<>());
                break;
        }

        Practice.getArenaManager().registerArena(arena, uuid);
        return arena;
    }

}
