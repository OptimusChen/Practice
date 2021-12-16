package com.optimus.practice.arena.arenas;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.optimus.practice.arena.Arena;
import com.optimus.practice.arena.ArenaConfiguration;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import com.optimus.practice.util.ItemCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class BoxingArena extends Arena {

    private HashMap<Player, Integer> combo;

    public BoxingArena(ArenaConfiguration config) {
        super(config);
        combo = new HashMap<>();
    }

    @Override
    public void giveKits() {
        for (Player player : getPlayers()) {
            player.getInventory().addItem(ItemCreator.createInventoryItem(Material.WOOD_SWORD, 1, 0, "&e&lBoxing Sword", true, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 5));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 5));
        }
    }

    @Override
    public void start(Player player1, Player player2) {
        super.start(player1, player2);
        combo.put(player1, 0);
        combo.put(player2, 0);
    }

    @Override
    public void end(PracticePlayer loser) {
        super.end(loser);

        for (Player player : getPlayers()) {
            player.removePotionEffect(PotionEffectType.SPEED);
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player.removePotionEffect(PotionEffectType.SATURATION);
        }
    }

    @EventHandler
    public void onCombo(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Player) {
                Player damager = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();

                if (getPlayers().contains(damager) && getPlayers().contains(victim)) {
                    combo.put(damager, combo.get(damager) + 1);
                }

                for (int i = 0; i < getPlayers().size(); i++) {
                    Player player = getPlayers().get(i);
                    if (combo.get(player) >= 100) {
                        if (getPlayers().get(0).equals(player)) {
                            end(PracticePlayerManager.getPlayer(getPlayers().get(1)));
                        } else {
                            end(PracticePlayerManager.getPlayer(getPlayers().get(0)));
                        }
                    }
                    if (i == 0) {
                        ActionBarAPI.sendActionBar(player, ChatColor.AQUA + "Your Hits: " + combo.get(player) + ChatColor.RED + " Their Hits: " + combo.get(getPlayers().get(1)));
                    } else {
                        ActionBarAPI.sendActionBar(player, ChatColor.AQUA + "Your Hits: " + combo.get(player) + ChatColor.RED + " Their Hits: " + combo.get(getPlayers().get(0)));
                    }
                }
            }
        }
    }
}
