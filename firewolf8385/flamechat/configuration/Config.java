package firewolf8385.flamechat.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;

public class Config
{

    private Config() {}
    static Config instance = new Config();

    /**
     * This allows us to access an instance of this class.
     */
    public static Config getInstance()
    {
        return instance;
    }

    Plugin pl;
    FileConfiguration config;
    File configFile;


    /**
     * This allows us to set up the config file if it does not exist.
     * @param pl Instance of the Plugin
     */
    public void setup(Plugin pl)
    {
        config = pl.getConfig();
        config.options().copyDefaults(true);
        configFile = new File(pl.getDataFolder(), "config.yml");
        pl.saveDefaultConfig();
    }

    /**
     * Allows us to access the config file.
     * @return config file
     */
    public FileConfiguration getConfig()
    {
        return config;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void saveConfig()
    {
        try
        {
            config.save(configFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This updates the config in case changes are made.
     */
    public void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(configFile);
    }
}