package com.optimus.practice.arena.arenas;

import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaConfiguration;
import lombok.Getter;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PvPArena extends Arena {

    private List<ItemStack> items;
    private List<ItemStack> armor;

    public PvPArena(ArenaConfiguration config, List<ItemStack> items, List<ItemStack> armor) {
        super(config);
        this.items = items;
        this.armor = armor;
    }

    @Override
    public void giveKits() {
        for (Player player : getPlayers()){
            for (ItemStack item : items){
                player.getInventory().addItem(item);
            }

            for (ItemStack armorItem : armor){
                String typeNameString = armorItem.getType().name();
                if (typeNameString.endsWith("_HELMET")){
                    player.getInventory().setHelmet(armorItem);
                }else if (typeNameString.endsWith("_CHESTPLATE")){
                    player.getInventory().setChestplate(armorItem);
                }else if (typeNameString.endsWith("_LEGGINGS")){
                    player.getInventory().setLeggings(armorItem);
                }else if (typeNameString.endsWith("_BOOTS")){
                    player.getInventory().setBoots(armorItem);
                }
            }
        }
    }
}
