package com.github.firewolf8385.flamechat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Settings
{
    static Settings instance = new Settings();

    public static Settings getInstance()
    {
        return instance;
    }

    FileConfiguration config;
    File configFile;

    FileConfiguration formats;
    File formatsFile;

    public void setup(Plugin pl)
    {
        config = pl.getConfig();
        config.options().copyDefaults(true);
        configFile = new File(pl.getDataFolder(), "config.yml");
        pl.saveConfig();

        formatsFile = new File(pl.getDataFolder(), "formats.yml");

        if(!formatsFile.exists())
            pl.saveResource("formats.yml", false);

        formats = YamlConfiguration.loadConfiguration(formatsFile);
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public FileConfiguration getFormats()
    {
        return formats;
    }

    public void saveFormats() {
        try {
            formats.save(formatsFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadFormats() {
        //saveFormats();
        formats = YamlConfiguration.loadConfiguration(formatsFile);
    }
}
