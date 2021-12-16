package com.optimus.practice.commands;

import com.optimus.practice.inventory.PlayerInventory;
import com.optimus.practice.util.Request;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RequestCommand extends PracticeCommand {

    public RequestCommand() {
        super("request", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("inventory")) {
                String requestToken = args[1];
                if (Request.isValid(requestToken)) {
                    Player inventoryPlayer = Bukkit.getPlayer(UUID.fromString(requestToken));
                    if (inventoryPlayer.isOnline()) {
                        PlayerInventory playerInventory = new PlayerInventory(inventoryPlayer);
                        playerInventory.open(player);
                    } else {
                        player.sendMessage(ChatColor.RED + "Player has disconnected!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid Inventory! Expired Token?");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid argument!");
            }
        }
    }
}
