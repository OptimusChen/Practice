package com.optimus.practice.arena;

import com.optimus.practice.Config.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public abstract class Arena {

    private ArrayList<Location> spawns;
    private int playerAmount;
    private ArenaConfiguration config;
    private String internalID;
    private String name;
    private boolean active;
    private ArrayList<Player> players;
    private ArrayList<Location> corners;

    public Arena(ArenaConfiguration config){
        this.config = config;
        this.playerAmount = 2;
        this.spawns = (ArrayList<Location>) config.getValue("spawns");
        this.corners = (ArrayList<Location>) config.getValue("corners");
        this.name = (String) config.getValue("name");
        this.internalID = (String) config.getValue("id");
        this.players = new ArrayList<>();
    }

    public abstract void giveKits();

    public void start(Player player1, Player player2){
        players.add(player1);
        players.add(player2);
        active = true;
        giveKits();
        player1.teleport(spawns.get(0));
        player2.teleport(spawns.get(1));
    }

    public void end(){
        for (Player player : players){
            player.teleport((Location) Config.getValue(Config.LOBBYLOCATION));
            player.getInventory().clear();
            for (ItemStack item : (ArrayList<ItemStack>) Config.getValue(Config.LOBBYITEMS)){
                player.getInventory().addItem(item);
            }
        }
        players.clear();
        active = false;
    }

}
