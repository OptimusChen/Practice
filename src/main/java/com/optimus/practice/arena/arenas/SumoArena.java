package com.optimus.practice.arena.arenas;

import com.optimus.practice.Practice;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaConfiguration;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SumoArena extends Arena {

    public SumoArena(ArenaConfiguration config) {
        super(config);
    }

    @Override
    public void giveKits() {
        for (Player player : getPlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 5));
        }
    }

    @Override
    public void start(Player player1, Player player2) {
        super.start(player1, player2);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isActive()) cancel();

                for (Player player : getPlayers()) {
                    if (player.getLocation().getBlock().getType().equals(Material.WATER) || player.getLocation().getBlock().getType().equals(Material.STATIONARY_WATER)) {
                        end(PracticePlayerManager.getPlayer(player));
                        cancel();
                    }
                }
            }
        }.runTaskTimer(Practice.getInstance(), 5L, 20);
    }

    @Override
    public void end(PracticePlayer loser) {
        loser.getPlayer().getWorld().strikeLightning(loser.getPlayer().getLocation());
        for (Player player : getPlayers()) {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.SATURATION);
        }

        super.end(loser);
    }
}
