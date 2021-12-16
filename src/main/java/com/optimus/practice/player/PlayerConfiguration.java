package com.optimus.practice.player;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class PlayerConfiguration {

    private FileConfiguration config;
    private final File file;

    public PlayerConfiguration(File file, FileConfiguration config) {
        this.file = file;
        this.config = config;
    }

    public Object getValue(String path) {
        return config.get(path);
    }

    public void setValue(String path, Object item) {
        try {
            config.set(path, item);
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

}
