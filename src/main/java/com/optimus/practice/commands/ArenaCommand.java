package com.optimus.practice.commands;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaType;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaCommand extends PracticeCommand {

    public ArenaCommand() {
        super("arena", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);
        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
        if (practicePlayer.getWandLocs().size() == 2) {
            if (args.length == 1) {
                try {
                    ArenaType type = ArenaType.valueOf(args[0]);
                    Arena arena = Practice.getArenaManager().createArena(type, practicePlayer.getSpawnLocations(), practicePlayer.getWandLocs());
                    player.sendMessage(ChatColor.GREEN + "Success creating arena with UUID: " + arena.getInternalID() + " !");
                } catch (IllegalArgumentException e) {
                    player.sendMessage(ChatColor.RED + "Error! IllegalArgumentException");

                }
            }
        }
    }
}
