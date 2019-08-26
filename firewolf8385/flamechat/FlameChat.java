package firewolf8385.flamechat;

import firewolf8385.flamechat.commands.FC;
import firewolf8385.flamechat.configuration.Blacklist;
import firewolf8385.flamechat.configuration.Config;
import firewolf8385.flamechat.configuration.Messages;
import org.bukkit.plugin.java.JavaPlugin;

public class FlameChat extends JavaPlugin
{
    /***************************************************************************************
     *    Title: FlameChat
     *    Author: firewolf8385
     *    Date: August 24th, 2019
     *    Code version: 0.1 Alpha
     ***************************************************************************************/
    private static FlameChat plugin;
    Config config = Config.getInstance();
    Blacklist blacklist = Blacklist.getInstance();
    Messages messages = Messages.getInstance();

    @Override
    public void onEnable()
    {
        plugin = this;

        // Enables bStats
        MetricsLite metrics = new MetricsLite(this);

        // registers commands and events
        registerCommands();
        registerEvents();

        // Sets up config files
        config.setup(this);
        blacklist.setup(this);
        messages.setup(this);
    }

    @Override
    public void onDisable()
    {
        // Supposedly this prevents Memory Leaks.
        // If you have evidence it does or does not,
        // let me know.
        plugin = null;
    }

    /**
     * Registers the events used by the plugin.
     */
    private void registerEvents()
    {

    }

    /**
     * Registers the commands used by the plugin.
     */
    private void registerCommands()
    {
        getCommand("flamechat").setExecutor(new FC());
    }

    /**
     * Retrieve an instance of plugin
     * @return plugin
     */
    public static FlameChat get()
    {
        return plugin;
    }
}