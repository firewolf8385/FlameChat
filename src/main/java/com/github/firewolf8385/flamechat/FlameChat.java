package com.github.firewolf8385.flamechat;

import com.github.firewolf8385.flamechat.commands.FlameChatCMD;
import com.github.firewolf8385.flamechat.commands.MessageCMD;
import com.github.firewolf8385.flamechat.commands.ReplyCMD;
import com.github.firewolf8385.flamechat.commands.SocialSpyCMD;
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

        getCommand("flamechat").setExecutor(new FlameChatCMD());
        getCommand("message").setExecutor(new MessageCMD());
        getCommand("reply").setExecutor(new ReplyCMD());
        getCommand("socialspy").setExecutor(new SocialSpyCMD());

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