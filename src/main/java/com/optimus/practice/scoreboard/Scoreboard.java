package com.optimus.practice.scoreboard;

import com.optimus.practice.player.PracticePlayer;
import com.optimus.practice.player.PracticePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class Scoreboard {

    private org.bukkit.scoreboard.Scoreboard board;
    private Objective objective;

    private final Player player;

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

    private void initScoreboard(){
        PracticePlayer practicePlayer = PracticePlayerManager.getPlayer(player);
        objective.setDisplayName(colorize("&d&lPractice"));
        addLine(6, "&7------------------");
        addLine(5, "&fWins: &d" + practicePlayer.getWins());
        addLine(4, "&fLosses: &d" + practicePlayer.getLosses());
        addLine(3, "&fPoints: &d" + practicePlayer.getPoints());
        addLine(2, "&fGlobal Ranking: &d" + practicePlayer.getGlobalRanking());
        addLine(1, "&7-------------------");
    }

    public void updateScoreboard(){
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("PracticeBoard", "Display ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("Practice");
        this.objective = objective;
        initScoreboard();
        player.setScoreboard(board);
    }

    // Yoinked from PluginUtils lol
    public void addLine(int line, String text){
        Score score = objective.getScore(colorize(text));
        score.setScore(line);
    }

    private String colorize(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
