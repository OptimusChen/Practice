package com.optimus.practice.inventory;

import com.optimus.practice.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class RankedQueueInventory extends PracticeInventory {

    public RankedQueueInventory() {
        super(true);
    }

    @Override
    public void init() {
        inventory = Bukkit.createInventory(null, 27, ChatColor.YELLOW + "" + ChatColor.BOLD + "Ranked Queue");
        fill(ItemCreator.createEmptySpace());
        inventory.setItem(13, ItemCreator.createInventoryItem(Material.DIAMOND_SWORD, 1, 0, "&e&lSolos", true, false, "&71v1s with a system", "&7where you earn", "&7and lose points", "&7when you win/lose.", "", "&eClick to play!"));
    }
}
