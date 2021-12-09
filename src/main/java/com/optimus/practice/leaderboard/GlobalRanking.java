package com.optimus.practice.leaderboard;

import com.optimus.practice.Practice;
import com.optimus.practice.player.PracticePlayer;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class GlobalRanking {

    private Practice practice = Practice.getInstance();
    private PracticePlayer practicePlayer;

    public GlobalRanking(PracticePlayer player){
        this.practicePlayer = player;
    }

    public int getRank(){
        return calculate();
    }

    private int calculate(){
        File folder = new File(practice.getDataFolder() + File.separator + "Players");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (folder.listFiles() != null){
            ArrayList<Integer> list = new ArrayList<>();

            for (File file : Objects.requireNonNull(folder.listFiles())) {
                FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(file);
                list.add(playerConfig.getInt("Points"));

            }

            Integer[] array = list.toArray(new Integer[Objects.requireNonNull(folder.listFiles()).length]);
            Arrays.sort(array);
            ArrayUtils.reverse(array);


            for (int j = 0; j < array.length; j++){
                if (array[j] == practicePlayer.getPoints()){
                    return j;
                }
            }
        }
        return 0;
    }

}
