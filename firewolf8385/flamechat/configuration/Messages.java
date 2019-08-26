package firewolf8385.flamechat.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Messages
{

    private Messages() {}
    static Messages instance = new Messages();

    /**
     * This allows us to access an instance of this class.
     */
    public static Messages getInstance()
    {
        return instance;
    }

    FileConfiguration messages;
    File messagesFile;


    /**
     * This allows us to set up the config file if it does not exist.
     * @param pl Instance of the Plugin
     */
    public void setup(Plugin pl)
    {
        messagesFile = new File(pl.getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        if(!messagesFile.exists())
        {
            try
            {
                messagesFile.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }


            messages.set("NoPermission", "&6&lError &8» &cYou do not have access to that command.");
            save();
        }
    }


    /**
     * Allows us to access the data file.
     * @return blacklist file
     */
    public FileConfiguration get()
    {
        return messages;
    }

    /**
     * Allows us to save the config file after changes are made.
     */
    public void save()
    {
        try
        {
            messages.save(messagesFile);
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
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
}