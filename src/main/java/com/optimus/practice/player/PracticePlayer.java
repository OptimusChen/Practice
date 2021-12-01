package com.optimus.practice.player;

import com.optimus.practice.scoreboard.Scoreboard;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import lombok.Getter;

public class PracticePlayer {

    @Getter @Setter
    public PlayerConfiguration config;
    @Getter
    private int wins;
    @Getter
    private int losses;
    @Getter
    private int points;
    private int globalRanking;
    private Scoreboard board;
    @Getter
    private Player player;

    public PracticePlayer(Player player){
        this.wins = 0;
        this.losses = 0;
        this.points = 0;
        this.globalRanking = 0;
        this.player = player;
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
        return globalRanking;
    }

    public void update() {
        config.update();
        try {
            wins = (int) config.getValue("Wins");
            losses = (int) config.getValue("Losses");
            points = (int) config.getValue("Points");
        }catch (NullPointerException e){
            Bukkit.getLogger().info("Config: " + config);
            Bukkit.getLogger().info("Val: " + config.getValue("Wins"));
            Bukkit.getLogger().info("int: " + (int) config.getValue("Wins"));
        }
        board.updateScoreboard();
    }
}
