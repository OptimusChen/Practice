package com.optimus.practice.queue;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import com.optimus.practice.scoreboard.ScoreboardState;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class Queue {

    private List<PracticePlayer> players;

    public Queue(){
        players = new ArrayList<>();
        init();
    }

    private void init(){
        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(Practice.getInstance(), 5L, 40);
    }

    public void join(PracticePlayer player){
        if (!players.contains(player)){
            players.add(player);
        }
    }

    public void leave(PracticePlayer player){
        players.remove(player);
    }

    public void update(){
        if (players.size() >= 2){
            boolean hasStarted = false;

            while (!hasStarted){
                for (Map.Entry<UUID, Arena> entry : Practice.getArenaManager().getRegistered().entrySet()){
                    Arena arena = entry.getValue();
                    if (!arena.isActive()){
                        if (new Random().nextInt(3) == 1){
                            arena.start(players.get(0).getPlayer(), players.get(1).getPlayer());
                            hasStarted = true;
                        }
                    }
                }
            }

            PracticePlayer player1 = players.get(0);
            PracticePlayer player2 = players.get(1);

            player1.setOpponent(player2);
            player2.setOpponent(player1);

            players.remove(0);
            players.remove(0);
        }
    }

}
