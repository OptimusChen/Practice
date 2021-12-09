package com.optimus.practice.arena;

import com.optimus.practice.Practice;
import com.optimus.practice.config.PracticeConfiguration;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import com.optimus.practice.scoreboard.ScoreboardState;
import com.optimus.practice.util.ItemCreator;
import com.optimus.practice.util.Request;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public abstract class Arena implements Listener {

    private ArrayList<Location> spawns;
    private int playerAmount;
    private ArenaConfiguration config;
    private String internalID;
    private String name;
    private boolean active;
    private ArrayList<Player> players;
    private ArrayList<Location> corners;
    private boolean finishedCountdown;

    public Arena(ArenaConfiguration config){
        this.config = config;
        this.playerAmount = 2;
        this.spawns = (ArrayList<Location>) config.getValue("spawns");
        this.corners = (ArrayList<Location>) config.getValue("corners");
        this.name = (String) config.getValue("name");
        this.internalID = (String) config.getValue("id");
        this.players = new ArrayList<>();
        this.finishedCountdown = false;
        Bukkit.getPluginManager().registerEvents(this, Practice.getInstance());
    }

    public abstract void giveKits();

    public void start(Player player1, Player player2){
        players.add(player1);
        players.add(player2);
        active = true;
        finishedCountdown = false;
        player1.getInventory().clear();
        player2.getInventory().clear();
        for (Player player : players){
            PracticePlayerManager.getPlayer(player).setScoreboardState(ScoreboardState.IN_GAME);
            player.setGameMode(GameMode.ADVENTURE);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.GREEN + "Game Starts in 3....");
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 10, 2);
                }
            }.runTaskLater(Practice.getInstance(), 20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.GREEN + "Game Starts in 2....");
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 10, 2);
                }
            }.runTaskLater(Practice.getInstance(), 2*20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.GREEN + "Game Starts in 1....");
                    player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 10, 2);
                }
            }.runTaskLater(Practice.getInstance(), 3*20);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.GREEN + "Game Starts Now!");
                    player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 2);
                    finishedCountdown = true;
                }
            }.runTaskLater(Practice.getInstance(), 4*20);
        }
        giveKits();
        player1.teleport(spawns.get(0));
        player2.teleport(spawns.get(1));
    }

    public void end(PracticePlayer loser){

        PracticePlayer winner;

        if (players.get(0).equals(loser.getPlayer())) {
            winner = PracticePlayerManager.getPlayer(players.get(1));
        }else{
            winner = PracticePlayerManager.getPlayer(players.get(0));
        }

        loser.getPlayer().teleport(winner.getPlayer().getLocation());
        loser.getPlayer().setGameMode(GameMode.SPECTATOR);

        List<PracticePlayer> practicePlayers = new ArrayList<>();
        practicePlayers.add(winner);
        practicePlayers.add(loser);

        Request.addRequest(winner.getPlayer().getUniqueId().toString(), 60);
        Request.addRequest(loser.getPlayer().getUniqueId().toString(), 60);

        for (PracticePlayer practicePlayer : practicePlayers){
            practicePlayer.getInventory().clear();

            for (int i = 0; i < 9; i++){
                practicePlayer.getInventory().put(i, practicePlayer.getPlayer().getInventory().getContents()[i]);
            }

            for (int i = practicePlayer.getPlayer().getInventory().getContents().length - 1; i > 0; i--){
                practicePlayer.getInventory().put(i, practicePlayer.getPlayer().getInventory().getContents()[i]);
            }

            for (int i = 0; i < practicePlayer.getPlayer().getInventory().getArmorContents().length; i++){
                practicePlayer.getArmor().put(i, practicePlayer.getPlayer().getInventory().getArmorContents()[i]);
            }

            practicePlayer.getPlayer().getInventory().clear();
            practicePlayer.getPlayer().getInventory().setArmorContents(new ItemStack[4]);
            practicePlayer.sendMessage("&aWinner: &7" + winner.getPlayer().getName());
            practicePlayer.sendMessage("&e&lMatch Results: &r&7(Click Player to View)");

            TextComponent messageWinner = new TextComponent("Winner: ");
            messageWinner.setColor(net.md_5.bungee.api.ChatColor.GREEN);

            ComponentBuilder cbWinner = new ComponentBuilder(
                    net.md_5.bungee.api.ChatColor.YELLOW + "Click to view " + net.md_5.bungee.api.ChatColor.GREEN
                            + winner.getPlayer().getName() + net.md_5.bungee.api.ChatColor.YELLOW + "'s inventory");

            TextComponent clickWinner = new TextComponent(winner.getPlayer().getName());
            clickWinner.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
            clickWinner.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cbWinner.create()));
            clickWinner.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/request inventory " + winner.getPlayer().getUniqueId().toString()));

            TextComponent messageLoser = new TextComponent(net.md_5.bungee.api.ChatColor.GRAY + " | " + net.md_5.bungee.api.ChatColor.RED + "Loser: ");

            ComponentBuilder cbLoser = new ComponentBuilder(
                    net.md_5.bungee.api.ChatColor.YELLOW + "Click to view " + net.md_5.bungee.api.ChatColor.RED
                            + loser.getPlayer().getName() + net.md_5.bungee.api.ChatColor.YELLOW + "'s inventory");

            TextComponent clickLoser = new TextComponent(loser.getPlayer().getName());
            clickLoser.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
            clickLoser.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, cbLoser.create()));
            clickLoser.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/request inventory " + loser.getPlayer().getUniqueId().toString()));

            messageWinner.addExtra(clickWinner);
            messageWinner.addExtra(messageLoser);
            messageWinner.addExtra(clickLoser);

            practicePlayer.sendMessage(messageWinner);

            practicePlayer.getPlayer().playSound(practicePlayer.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 10, 1);
            for (int i = 0; i < 20; i++){
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (practicePlayer.getPlayer().isOnline()){
                            practicePlayer.getPlayer().playSound(practicePlayer.getPlayer().getLocation(), Sound.FIREWORK_LAUNCH, 10, 1);

                        }
                    }
                }.runTaskLater(Practice.getInstance(), i*10);
            }
        }

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.GOLD + "Victory!" + "\"}");

        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle length = new PacketPlayOutTitle(5, 50, 5);

        ((CraftPlayer) winner.getPlayer()).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer) winner.getPlayer()).getHandle().playerConnection.sendPacket(length);

        IChatBaseComponent chatTitle2 = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + ChatColor.RED + "Defeat!" + "\"}");

        PacketPlayOutTitle title2 = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle2);
        PacketPlayOutTitle length2 = new PacketPlayOutTitle(5, 50, 5);

        ((CraftPlayer) loser.getPlayer()).getHandle().playerConnection.sendPacket(title2);
        ((CraftPlayer) loser.getPlayer()).getHandle().playerConnection.sendPacket(length2);

        winner.getConfig().setValue("Wins", (int) winner.getConfig().getValue("Wins") + 1);
        loser.getConfig().setValue("Losses", (int) loser.getConfig().getValue("Losses") + 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (PracticePlayer pplayer : practicePlayers){
                    if (pplayer.getPlayer().isOnline()) {
                        Player player = pplayer.getPlayer();
                        player.teleport((Location) Practice.getConfiguration().getValue(PracticeConfiguration.LOBBYLOCATION));
                        PracticePlayerManager.getPlayer(player).setScoreboardState(ScoreboardState.LOBBY);
                        player.getInventory().setItem(0, ItemCreator.createInventoryItem(
                                Material.IRON_SWORD, 1, 0, "&dUnranked Practice &7(Right Click)", true, false));
                        player.getInventory().setItem(1, ItemCreator.createInventoryItem(
                                Material.DIAMOND_SWORD, 1, 0, "&dRanked Practice &7(Right Click)", true, false));
                        player.getInventory().setItem(7, ItemCreator.createInventoryItem(
                                Material.EMERALD, 1, 0, "&dLeaderboards &7(Right Click)", true, true));
                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            player.removePotionEffect(effect.getType());
                        }
                        loser.getPlayer().setGameMode(GameMode.ADVENTURE);
                    }
                    players.clear();
                    active = false;
                }
            }
        }.runTaskLater(Practice.getInstance(), 10*20);

    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            if (players.contains(player)){
                if (player.getHealth() - e.getFinalDamage() <= 0){
                    end(PracticePlayerManager.getPlayer(player));
                    player.setHealth(20.0f);
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (players.contains(e.getPlayer())){
            if (!finishedCountdown){
                 e.setCancelled(true);
            }
        }
    }

}
