package com.optimus.practice.commands;

import com.optimus.practice.inventory.AdminCommandInventory;
import org.bukkit.entity.Player;

public class AdminCommand extends PracticeCommand {

    public AdminCommand() {
        super("practiceadmin", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);

        new AdminCommandInventory().open(player);
    }
}
