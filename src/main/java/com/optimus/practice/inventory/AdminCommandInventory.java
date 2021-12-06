package com.optimus.practice.inventory;

import com.optimus.practice.util.ItemCreator;
import org.bukkit.Bukkit;

public class AdminCommandInventory extends PracticeInventory {

    public AdminCommandInventory() {
        super(true);
    }

    @Override
    public void init() {
        inventory = Bukkit.createInventory(null, 54, "Admin Panel");
        fill(ItemCreator.createEmptySpace());
    }
}
