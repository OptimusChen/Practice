package com.optimus.practice.util;

import com.optimus.practice.arena.ArenaType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class KitCreator {

    public static List<ItemStack> createKit(ArenaType type){
        List<ItemStack> list = new ArrayList<>();
        switch (type){
            case NODEBUFF:
                list.add(ItemCreator.addEnchs(new ItemStack(Material.DIAMOND_SWORD), Enchantment.FIRE_ASPECT, 2, Enchantment.DAMAGE_ALL, 3, Enchantment.DURABILITY, 3));
                Potion fireRes = Potion.fromItemStack(new ItemStack(Material.POTION));
                fireRes.setSplash(false);
                fireRes.setType(PotionType.FIRE_RESISTANCE);
                fireRes.setHasExtendedDuration(true);
                fireRes.setLevel(1);
                list.add(fireRes.toItemStack(1));

                Potion speed = Potion.fromItemStack(new ItemStack(Material.POTION));
                speed.setSplash(false);
                speed.setType(PotionType.SPEED);
                speed.setHasExtendedDuration(false);
                speed.setLevel(2);
                list.add(speed.toItemStack(1));

                list.add(new ItemStack(Material.ENDER_PEARL, 16));
                list.add(new ItemStack(Material.COOKED_BEEF, 64));

                for (int i = 0; i < 29; i++){
                    Potion potion = Potion.fromItemStack(new ItemStack(Material.POTION));
                    potion.setSplash(true);
                    potion.setType(PotionType.INSTANT_HEAL);
                    potion.setLevel(2);
                    list.add(potion.toItemStack(1));
                }

                list.add(speed.toItemStack(2));
                break;
        }
        return list;
    }

    public static List<ItemStack> createArmor(ArenaType type) {
        List<ItemStack> list = new ArrayList<>();
        switch (type){
            case NODEBUFF:
                list.add(ItemCreator.addEnchs(new ItemStack(Material.DIAMOND_HELMET), Enchantment.PROTECTION_ENVIRONMENTAL, 2, Enchantment.DURABILITY, 3, null, 0));
                list.add(ItemCreator.addEnchs(new ItemStack(Material.DIAMOND_CHESTPLATE), Enchantment.PROTECTION_ENVIRONMENTAL, 2, Enchantment.DURABILITY, 3, null, 0));
                list.add(ItemCreator.addEnchs(new ItemStack(Material.DIAMOND_LEGGINGS), Enchantment.PROTECTION_ENVIRONMENTAL, 2, Enchantment.DURABILITY, 3, null, 0));
                list.add(ItemCreator.addEnchs(new ItemStack(Material.DIAMOND_BOOTS), Enchantment.PROTECTION_ENVIRONMENTAL, 3, Enchantment.DURABILITY, 3, null, 0));
                break;
        }
        return list;
    }
}
