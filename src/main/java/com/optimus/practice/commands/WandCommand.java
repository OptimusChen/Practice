package com.optimus.practice.commands;

import com.optimus.practice.wand.Wand;
import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.entity.Player;

public class WandCommand extends PracticeCommand {

    public WandCommand() {
        super("wand", "", true);
    }

    @Override
    public void execute(Player player, String[] args) {
        super.execute(player, args);
        PracticePlayer player1 = PracticePlayerManager.getPlayer(player);
        if (player1 != null){
            player1.setWand(new Wand(player1));
        }
    }
}
