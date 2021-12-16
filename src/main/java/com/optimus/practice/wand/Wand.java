package com.optimus.practice.wand;

import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.util.ItemCreator;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public class Wand {

    public static final Material wandMaterial = Material.CARROT_STICK;
    private final ItemStack wand;
    private final PracticePlayer player;

    public Wand(PracticePlayer player) {
        this.player = player;
        this.wand = new ItemStack(wandMaterial);
        player.getPlayer().getInventory().addItem(ItemCreator.createWand());
    }

    public void click(Location loc, boolean left) {
        player.setWandLocations(loc, left);
    }
}
