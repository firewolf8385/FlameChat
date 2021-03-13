package com.github.firewolf8385.flamechat;

import com.github.firewolf8385.flamechat.commands.*;
import com.github.firewolf8385.flamechat.listeners.PlayerChatListener;
import org.bukkit.plugin.java.JavaPlugin;

public class FlameChat extends JavaPlugin {
    private static FlameChat plugin;
    private static final Settings settings = Settings.getInstance();

    /**
     * Get an instance of the plugin.
     * @return Instance of plugin.
     */
    public static FlameChat getPlugin()
    {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        settings.setup(this);
        registerListeners();

        new MetricsLite(this, 5415);

        AbstractCommand.registerCommands(this);

        settings.reloadFormats();
    }

    @Override
    public void onDisable() {
        plugin = null;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
    }
}