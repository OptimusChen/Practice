package com.optimus.practice.util;

import com.optimus.practice.arena.Wand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.invoke.SerializedLambda;
import java.util.ArrayList;
import java.util.List;

public class ItemCreator {

    public static ItemStack createEmptySpace(){
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createExitBarrier(){
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Close");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createInventoryItem(Material mat, int amount, int id, String name, boolean hideFlags, boolean glow, String... strings) {
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        item.setDurability((short)id);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        for (String s : strings){
            lore.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        if (hideFlags){
            meta.addItemFlags(ItemFlag.values());
            meta.spigot().setUnbreakable(true);
        }
        if (glow){
            meta.addEnchant(Enchantment.LURE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createWand(){
        ItemStack item = new ItemStack(Wand.wandMaterial);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Practice Wand");
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addEnchs(ItemStack item, Enchantment enchantment, int level, Enchantment enchantment2, int level2, Enchantment enchantment3, int level3){
        if (enchantment != null) {
            item.addEnchantment(enchantment, level);
        }
        if (enchantment2 != null) {
            item.addEnchantment(enchantment2, level2);
        }
        if (enchantment3 != null){
            item.addEnchantment(enchantment3, level3);
        }
        return item;
    }

}
