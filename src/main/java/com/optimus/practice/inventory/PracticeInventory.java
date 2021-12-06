package com.optimus.practice.inventory;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class PracticeInventory {

    public Inventory inventory = Bukkit.createInventory(null, 54, "Default Practice Inventory");

    public PracticeInventory(boolean immediateInnit){
        if (immediateInnit){
            init();
        }
    }

    public abstract void init();

    public void open(final HumanEntity player){
        player.openInventory(inventory);
    }

    public void fill(ItemStack item){
        for(int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, item);
        }
    }

}
