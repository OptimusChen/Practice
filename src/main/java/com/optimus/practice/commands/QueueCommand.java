package com.optimus.practice.commands;

import com.optimus.practice.Practice;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QueueCommand extends PracticeCommand {

    public QueueCommand() {
        super("queue", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);
        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);

        if (args.length == 2){
            if (args[0].equalsIgnoreCase("join")){
                if (args[1].equalsIgnoreCase("unranked")) {
                    Practice.getUnrankedQueue().join(practicePlayer);
                    player.sendMessage(ChatColor.GREEN + "Successfully joined unranked queue!");
                }else if (args[1].equalsIgnoreCase("ranked")) {
                    Practice.getRankedQueue().join(practicePlayer);
                    player.sendMessage(ChatColor.GREEN + "Successfully joined ranked queue!");
                }
            }
        }

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("leave")){
                Practice.getUnrankedQueue().leave(practicePlayer);
                Practice.getRankedQueue().leave(practicePlayer);
                player.sendMessage(ChatColor.GREEN + "Successfully left queue!");
            }
        }
    }
}
