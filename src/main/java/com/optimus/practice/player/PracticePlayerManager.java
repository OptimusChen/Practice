package com.optimus.practice.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PracticePlayerManager {

    private static HashMap<UUID, PracticePlayer> players = new HashMap();

    public static PracticePlayer getPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static void registerPlayer(Player player) {
        players.put(player.getUniqueId(), new PracticePlayer(player));
    }

    public static HashMap<UUID, PracticePlayer> getPlayers() {
        return players;
    }

    public static void unregisterPlayer(UUID uuid) {
        players.remove(uuid);
    }

}
