package com.optimus.practice.commands;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaType;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class QueueCommand extends PracticeCommand {

    public QueueCommand() {
        super("queue", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);
        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("join")) {
                ArenaType type = ArenaType.valueOf(args[2].toUpperCase());
                boolean found = false;

                for (Map.Entry<UUID, Arena> entry : Practice.getArenaManager().getRegistered().entrySet()) {
                    if (entry.getValue().getName().equalsIgnoreCase(type.name())) {
                        found = true;
                    }
                }

                if (found) {
                    if (args[1].equalsIgnoreCase("unranked")) {
                        Practice.getUnrankedQueue().join(practicePlayer, type);
                        player.sendMessage(ChatColor.GREEN + "Successfully joined unranked queue!");
                    } else if (args[1].equalsIgnoreCase("ranked")) {
                        Practice.getRankedQueue().join(practicePlayer, type);
                        player.sendMessage(ChatColor.GREEN + "Successfully joined ranked queue!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "No arenas for the given type were found!");
                }
            }
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("leave")) {
                Practice.getUnrankedQueue().leave(practicePlayer);
                Practice.getRankedQueue().leave(practicePlayer);
                player.sendMessage(ChatColor.GREEN + "Successfully left queue!");
            }
        }
    }
}
