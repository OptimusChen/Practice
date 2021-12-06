package com.optimus.practice.listeners;

import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WandListener implements Listener {



    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (e.getPlayer().getItemInHand() != null){
            if (e.getPlayer().getItemInHand().getItemMeta() != null){
                if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
                    if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("practice wand")){
                        e.setCancelled(true);
                        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(e.getPlayer());
                        if (practicePlayer.getWand() == null){
                            e.getPlayer().sendMessage(ChatColor.RED + "Error! Invalid Wand! Use /wand to get a new one!");
                            return;
                        }
                        practicePlayer.getWand().click(e.getBlock().getLocation(), false);
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully set location 1! " + practicePlayer.getWandLocs());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (e.getPlayer().getItemInHand() != null){
                if (e.getPlayer().getItemInHand().getItemMeta() != null){
                    if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()){
                        if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("practice wand")){
                            PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(e.getPlayer());
                            if (practicePlayer.getWand() == null){
                                e.getPlayer().sendMessage(ChatColor.RED + "Error! Invalid Wand! Use /wand to get a new one!");
                                return;
                            }
                            practicePlayer.getWand().click(e.getClickedBlock().getLocation(), true);
                            e.getPlayer().sendMessage(ChatColor.GREEN + "Successfully set location 2! " + practicePlayer.getWandLocs());
                        }
                    }
                }
            }
        }
    }

}
