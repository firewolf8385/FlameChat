package firewolf8385.flamechat;

import firewolf8385.flamechat.commands.FC;
import org.bukkit.plugin.java.JavaPlugin;

public class FlameChat extends JavaPlugin
{
    private static FlameChat plugin;

    /***************************************************************************************
     *    Title: FlameChat
     *    Author: firewolf8385
     *    Date: August 24th, 2019
     *    Code version: 0.1 Alpha
     ***************************************************************************************/

    @Override
    public void onEnable()
    {
        plugin = this;

        

        //registers commands and events
        registerCommands();
        registerEvents();
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
