package com.optimus.practice.inventory;

import com.optimus.practice.Practice;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerInventory extends PracticeInventory implements Listener {

    private Player player;

    public PlayerInventory(Player player){
        super(false);
        this.player = player;
        init();
    }

    @Override
    public void init() {
        inventory = Bukkit.createInventory(null, 45, player.getName() + "'s Inventory");

        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
        HashMap<Integer, ItemStack> armor = practicePlayer.getArmor();
        HashMap<Integer, ItemStack> inv = practicePlayer.getInventory();

        for (Map.Entry<Integer, ItemStack> entry : inv.entrySet()){
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        inventory.setItem(36, armor.get(3));
        inventory.setItem(37, armor.get(2));
        inventory.setItem(38, armor.get(1));
        inventory.setItem(39, armor.get(0));

        Bukkit.getPluginManager().registerEvents(this, Practice.getInstance());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (e.getInventory().equals(inventory)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (e.getInventory().equals(inventory)){
            HandlerList.unregisterAll(this);
        }
    }
}
