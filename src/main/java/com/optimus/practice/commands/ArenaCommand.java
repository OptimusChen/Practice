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
        if (practicePlayer.getWandLocs().size() == 2){
            Arena arena = Practice.getArenaManager().createArena(ArenaType.NODEBUFF, practicePlayer.getSpawnLocations(), practicePlayer.getWandLocs());
            player.sendMessage(ChatColor.GREEN + "Success!");
        }
    }
}
