package com.optimus.practice.listeners;

import com.optimus.practice.Main;
import com.optimus.practice.player.PlayerConfig;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
                practicePlayer.update();
            }
        }.runTaskTimer(Main.getInstance(), 5L, 40);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        PracticePlayerManager.unregisterPlayer(e.getPlayer().getUniqueId());
    }

}
