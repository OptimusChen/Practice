package com.optimus.practice.inventory;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.ArenaType;
import com.optimus.practice.util.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class UnrankedSelectModeInventory extends PracticeInventory {

    public UnrankedSelectModeInventory() {
        super(true);
    }

    @Override
    public void init() {
        inventory = Bukkit.createInventory(null, 45, ChatColor.translateAlternateColorCodes('&', "&e&lSolo Unranked Queue"));

        outline(ItemCreator.createEmptySpace());

        inventory.addItem(ItemCreator.createInventoryItem(Material.DIAMOND_SWORD, 1, 0, "&d&lNoDebuff", true, true, "", "&7In Queue: &d" + Practice.getUnrankedQueue().getAmount(ArenaType.NODEBUFF), "", "&eClick to join queue!"));
        inventory.addItem(ItemCreator.createInventoryItem(Material.LEASH, 1, 0, "&d&lSumo", true, true, "", "&7In Queue: &d" + Practice.getUnrankedQueue().getAmount(ArenaType.SUMO), "", "&eClick to join queue!"));
        inventory.addItem(ItemCreator.createInventoryItem(Material.DIAMOND_CHESTPLATE, 1, 0, "&d&lBoxing", true, true, "", "&7In Queue: &d" + Practice.getUnrankedQueue().getAmount(ArenaType.BOXING), "", "&eClick to join queue!"));

    }
}
