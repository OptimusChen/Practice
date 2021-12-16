package com.optimus.practice.scoreboard;

import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.text.DecimalFormat;

public class Scoreboard {

    private final Player player;
    private org.bukkit.scoreboard.Scoreboard board;
    private Objective objective;

    public Scoreboard(Player player) {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("PracticeBoard", "Display ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("Practice");
        this.objective = objective;
        this.player = player;
        initScoreboard();
        updateScoreboard();
    }

    private void initScoreboard() {
        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
        DecimalFormat formatter = new DecimalFormat("#,###");
        formatter.setGroupingUsed(true);
        switch (practicePlayer.getScoreboardState()) {
            case LOBBY:
                objective.setDisplayName(colorize("&d&lPractice"));
                addLine(6, "&7------------------");
                addLine(5, "&fWins: &d" + formatter.format(practicePlayer.getWins()));
                addLine(4, "&fLosses: &d" + formatter.format(practicePlayer.getLosses()));
                addLine(3, "&fPoints: &d" + formatter.format(practicePlayer.getPoints()));
                addLine(2, "&fGlobal Ranking: &d" + formatter.format(practicePlayer.getGlobalRanking()));
                addLine(1, "&7-------------------");
                break;
            case IN_GAME:
                EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
                PracticePlayer opponent = practicePlayer.getOpponent();
                EntityPlayer opponentPlayer = ((CraftPlayer) opponent.getPlayer()).getHandle();
                objective.setDisplayName(colorize("&d&lPractice"));
                addLine(6, "&7------------------");
                addLine(5, "&fOpponent: &d" + practicePlayer.getOpponent().getPlayer().getName());
                addLine(4, "&fPing: &d" + entityPlayer.ping);
                addLine(3, "&fOpponent Ping: &d" + opponentPlayer.ping);
                addLine(2, "&fOpponent Ranking: &d" + opponent.getGlobalRanking());
                addLine(1, "&7-------------------");
                break;
        }
    }

    public void updateScoreboard() {

        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("PracticeBoard", "Display ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("Practice");
        this.objective = objective;
        initScoreboard();
        player.setScoreboard(board);
    }

    // Yoinked from PluginUtils lol
    public void addLine(int line, String text) {
        Score score = objective.getScore(colorize(text));
        score.setScore(line);
    }

    private String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
