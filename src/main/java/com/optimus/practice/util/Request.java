package com.optimus.practice.util;

import com.optimus.practice.Practice;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Request {

    private static ArrayList<String> activeRequests = new ArrayList<>();

    public static boolean isValid(String s) {
        return activeRequests.contains(s);
    }

    public static void addRequest(String s, int expiry) {
        activeRequests.add(s);

        new BukkitRunnable() {
            @Override
            public void run() {
                activeRequests.remove(s);
            }
        }.runTaskLater(Practice.getInstance(), 20 * expiry);
    }

}
