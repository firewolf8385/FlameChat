package firewolf8385.flamechat.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Blacklist
{

    private Blacklist() {}
    static Blacklist instance = new Blacklist();

    /**
     * This allows us to access an instance of this class.
     */
    public static Blacklist getInstance()
    {
        return instance;
    }

    FileConfiguration blacklist;
    File blacklistFile;


    /**
     * This allows us to set up the config file if it does not exist.
     * @param pl Instance of the Plugin
     */
    public void setup(Plugin pl)
    {
        blacklistFile = new File(pl.getDataFolder(), "blacklist.yml");
        blacklist = YamlConfiguration.loadConfiguration(blacklistFile);

        if(!blacklistFile.exists())
        {
            try
            {
                blacklistFile.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }


            blacklist.set("enabled", false);
            blacklist.set("blacklist", Arrays.asList(""));
            save();
        }
    }


    /**
     * Allows us to access the data file.
     * @return blacklist file
     */
    public FileConfiguration get()
    {
        return blacklist;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void save()
    {
        try
        {
            blacklist.save(blacklistFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This updates the data in case changes are made.
     */
    public void reload()
    {
        blacklist = YamlConfiguration.loadConfiguration(blacklistFile);
    }
}