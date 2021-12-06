package com.optimus.practice.inventory;

import com.optimus.practice.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class UnrankedQueueInventory extends PracticeInventory {

    public UnrankedQueueInventory() {
        super(true);
    }

    @Override
    public void init() {
        inventory = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "" + ChatColor.BOLD + "Unranked Queue");
        fill(ItemCreator.createEmptySpace());
        inventory.setItem(13, ItemCreator.createInventoryItem(Material.DIAMOND_SWORD, 1, 0, "&e&lSolos", true, false, "&7Casual 1v1s with", "&7no loss penalty.", "", "&eClick to play!"));
    }
}
