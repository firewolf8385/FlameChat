package firewolf8385.flamechat;

import org.bukkit.plugin.java.JavaPlugin;

public class FlameChat extends JavaPlugin
{
    private static FlameChat plugin;

    @Override
    public void onEnable()
    {
        plugin = this;
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
