package com.optimus.practice.player;

import com.optimus.practice.leaderboard.GlobalRanking;
import com.optimus.practice.wand.Wand;
import com.optimus.practice.scoreboard.Scoreboard;
import com.optimus.practice.scoreboard.ScoreboardState;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

@Getter @Setter
public class PracticePlayer {

    public PlayerConfiguration config;
    private Wand wand;
    private int wins;
    private int losses;
    private int points;
    private GlobalRanking globalRanking;
    private Scoreboard board;
    private Player player;
    private ArrayList<Location> wandLocs;
    private ArrayList<Location> spawnLocations;
    private ScoreboardState scoreboardState;
    private PracticePlayer opponent;
    private HashMap<Integer, ItemStack> inventory;
    private HashMap<Integer, ItemStack> armor;

    public PracticePlayer(Player player){
        this.wins = 0;
        this.losses = 0;
        this.points = 0;
        this.player = player;
        this.wandLocs = new ArrayList<>();
        this.spawnLocations = new ArrayList<>();
        this.scoreboardState = ScoreboardState.LOBBY;
        this.opponent = null;
        this.inventory = new HashMap<>();
        this.armor = new HashMap<>();
        this.globalRanking = new GlobalRanking(this);
    }

    public void init(){
        this.board = new Scoreboard(player);
        try {
            wins = (int) config.getValue("Wins");
            losses = (int) config.getValue("Losses");
            points = (int) config.getValue("Points");
        }catch (NullPointerException e){

        }
    }

    public int getGlobalRanking(){
        return globalRanking.getRank();
    }

    public void update() {
        config.update();
        try {
            wins = (int) config.getValue("Wins");
            losses = (int) config.getValue("Losses");
            points = (int) config.getValue("Points");
        }catch (NullPointerException e){
        }
        board.updateScoreboard();
    }

    public void setSpawnLocations(Location loc, int index) {
        if (index == 1){
            if (spawnLocations.size() == 0){
                spawnLocations.add(loc);
            }else{
                spawnLocations.set(0, loc);
            }
        }else{
            if (spawnLocations.size() == 0){

            } else if (spawnLocations.size() == 1){
                spawnLocations.add(loc);
            }else{
                spawnLocations.set(1, loc);
            }
        }
    }

    public void setWandLocations(Location loc, boolean left) {
        if (!left) {
            if (wandLocs.size() == 0) {
                wandLocs.add(loc);
            } else {
                wandLocs.set(0, loc);
            }
        } else {
            if (wandLocs.size() == 0) {

            } else if (wandLocs.size() == 1) {
                wandLocs.add(loc);
            } else {
                wandLocs.set(1, loc);
            }
        }
    }

    public void sendMessage(Object message){
        if (message instanceof String){
            getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message.toString()));
        }else if (message instanceof TextComponent){
            getPlayer().spigot().sendMessage((TextComponent) message);
        }
    }

}
