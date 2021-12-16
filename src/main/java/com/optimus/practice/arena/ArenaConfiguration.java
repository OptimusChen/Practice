package com.optimus.practice.arena;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class ArenaConfiguration {

    private YamlConfiguration configuration;
    private File file;

    public ArenaConfiguration(File file) {
        this.file = file;
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public Object getValue(String path) {
        return configuration.get(path);
    }

    public void setObject(String path, Object item) {
        try {
            configuration.set(path, item);
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }

}
