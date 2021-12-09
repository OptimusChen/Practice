package com.optimus.practice.queue;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaType;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import com.optimus.practice.scoreboard.ScoreboardState;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public abstract class Queue {

    private HashMap<ArenaType, ArrayList<PracticePlayer>> players;

    public Queue(){
        players = new HashMap<>();
        init();
    }

    private void init(){
        for (ArenaType type : ArenaType.values()){
            players.put(type, new ArrayList<>());
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(Practice.getInstance(), 5L, 40);
    }

    public void join(PracticePlayer player, ArenaType type){
        ArrayList<PracticePlayer> list = players.get(type);
        if (!list.contains(player)){
            list.add(player);
        }
    }

    public void leave(PracticePlayer player) {
        for (Map.Entry<ArenaType, ArrayList<PracticePlayer>> entry : players.entrySet()) {
            for (PracticePlayer practicePlayer : entry.getValue()){
                if (practicePlayer.equals(player)){
                    entry.getValue().remove(player);
                }
            }
        }
    }

    public int getAmount(ArenaType type){
        return players.get(type).size();
    }

    public void update(){
        for (Map.Entry<ArenaType, ArrayList<PracticePlayer>> entry : players.entrySet()) {
            ArrayList<PracticePlayer> practicePlayers = entry.getValue();
            ArenaType type = entry.getKey();
            if (practicePlayers.size() >= 2){
                boolean hasStarted = false;

                while (!hasStarted){
                    for (Map.Entry<UUID, Arena> entry2 : Practice.getArenaManager().getRegistered().entrySet()){
                        Arena arena = entry2.getValue();
                        if (!arena.isActive()){
                            if (arena.getName().equalsIgnoreCase(type.name())){
                                arena.start(practicePlayers.get(0).getPlayer(), practicePlayers.get(1).getPlayer());
                                hasStarted = true;
                            }
                        }
                    }
                }

                PracticePlayer player1 = practicePlayers.get(0);
                PracticePlayer player2 = practicePlayers.get(1);

                player1.setOpponent(player2);
                player2.setOpponent(player1);

                practicePlayers.remove(player1);
                practicePlayers.remove(player2);
            }
        }
    }

}
