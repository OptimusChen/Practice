package com.optimus.practice.util;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WorldManager {

    private static List<World> worlds;

    public static String createNewMap(String mapName) {
        World world = Bukkit.getWorld(mapName);
        System.out.println(world);
        File worldDir = world.getWorldFolder();
        File uiddat = new File(worldDir + "\\uid.dat");
        System.out.println(uiddat.exists());
        uiddat.delete();
        String newName = world.getName() + "_" + System.currentTimeMillis();
        try {
            FileUtils.copyDirectory(worldDir, new File(worldDir.getParent(), newName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WorldCreator creator = new WorldCreator(newName);
        World newWorld = Bukkit.createWorld(creator);
        return newWorld.getName();
    }

    public static void deleteMap(String mapName) {
        System.out.println("deleting " + mapName);
        World world = Bukkit.getWorld(mapName);
        Bukkit.unloadWorld(world, false);
        delete(world.getWorldFolder());
        System.out.println(world.getWorldFolder());
        System.out.println(world.getWorldFolder().isDirectory());
    }

    private static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) return;
            for (File child : files) {
                delete(child);
            }
        }

        file.delete();
    }

}
