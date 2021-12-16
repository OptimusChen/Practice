package com.optimus.practice.commands;

import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnLocationCommand extends PracticeCommand {

    public SpawnLocationCommand() {
        super("spawnlocation", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);

        if (args.length == 0) {

        } else if (args.length == 1) {
            PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
            try {
                practicePlayer.setSpawnLocations(new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 5, player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch()), Integer.parseInt(args[0]));
                player.sendMessage(ChatColor.GREEN + "Success!");
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Not a Number!");
            }
        }
    }
}
