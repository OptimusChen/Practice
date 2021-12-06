package com.optimus.practice.arena;

import com.optimus.practice.config.ArenaConfig;
import com.optimus.practice.util.WorldManager;
import lombok.Getter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ArenaManager {

    @Getter
    private HashMap<UUID, Arena> registered;

    public void init(){
        registered = new HashMap<>();
        ArenaConfig.init();
    }

    public void registerArena(Arena arena, UUID uuid){
        registered.put(uuid, arena);
    }


    public Arena createArena(ArenaType type, ArrayList<Location> spawns, ArrayList<Location> corners){
        switch (type){
            case SUMO:
                break;
            case NODEBUFF:
                return ArenaConfig.createArenaConfig(type, spawns, corners);
        }
        return null;
    }
}
