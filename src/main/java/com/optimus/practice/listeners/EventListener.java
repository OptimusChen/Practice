package com.optimus.practice.listeners;

import com.optimus.practice.Practice;
import com.optimus.practice.config.PlayerConfig;
import com.optimus.practice.inventory.RankedQueueInventory;
import com.optimus.practice.inventory.UnrankedQueueInventory;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import com.optimus.practice.scoreboard.ScoreboardState;
import com.optimus.practice.util.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        PracticePlayerManager.registerPlayer(e.getPlayer());
        PlayerConfig.createPlayerFile(e.getPlayer());
        Player player = e.getPlayer();
        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
        practicePlayer.init();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (practicePlayer.getPlayer().isOnline()){
                    practicePlayer.update();
                    if (practicePlayer.getScoreboardState().equals(ScoreboardState.LOBBY)){
                        practicePlayer.getPlayer().setFoodLevel(20);
                        practicePlayer.getPlayer().setHealth(20.0f);
                        practicePlayer.getPlayer().getWorld().setGameRuleValue("keepInventory", "true");
                    }
                }else{
                    cancel();
                }
            }
        }.runTaskTimer(Practice.getInstance(), 5L, 40);
        player.getInventory().clear();
        player.getInventory().setItem(0, ItemCreator.createInventoryItem(Material.IRON_SWORD, 1, 0, "&dUnranked Practice &7(Right Click)", true, false));
        player.getInventory().setItem(1, ItemCreator.createInventoryItem(Material.DIAMOND_SWORD, 1, 0, "&dRanked Practice &7(Right Click)", true, false));
        player.getInventory().setItem(7, ItemCreator.createInventoryItem(Material.EMERALD, 1, 0, "&dLeaderboards &7(Right Click)", true, true));

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        PracticePlayerManager.unregisterPlayer(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if (item != null){
                if (item.getItemMeta() != null){
                    if (item.getItemMeta().hasDisplayName()){
                        if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Unranked Practice (Right Click)")){
                            if (item.getType().equals(Material.IRON_SWORD)){
                                new UnrankedQueueInventory().open(player);
                            }
                        }else if (ChatColor.stripColor(item.getItemMeta().getDisplayName()).equalsIgnoreCase("Ranked Practice (Right Click)")){
                            if (item.getType().equals(Material.DIAMOND_SWORD)){
                            new RankedQueueInventory().open(player);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Unranked Queue")){
            e.setCancelled(true);
            if (e.getSlot() == 13){
                player.performCommand("queue join unranked");
                player.closeInventory();
            }
        }else if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("Ranked Queue")){
            e.setCancelled(true);
            if (e.getSlot() == 13){
                player.performCommand("queue join ranked");
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e){
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
            if (practicePlayer != null){
                if (practicePlayer.getScoreboardState().equals(ScoreboardState.LOBBY)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
            if (practicePlayer.getScoreboardState().equals(ScoreboardState.LOBBY)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e){
        e.setCancelled(true);
    }

}
