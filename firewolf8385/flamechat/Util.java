package firewolf8385.flamechat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util
{

    /**
     * A quicker way to send a CommandSender a message.
     * @param sender The CommandSender to send a message to.
     * @param message The message to be sent.
     */
    public static void chat(CommandSender sender, String message)
    {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * A quicker way to send a player a message.
     * @param player The player to send a message to.
     * @param message The message to be sent.
     */
    public static void chat(Player player, String message)
    {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Logs a message to the console.
     * @param type Type of message to log.
     * @param message The message to log.
     */
    protected static void log(String type, String message)
    {
        switch(type)
        {
            case "info":
                Bukkit.getLogger().info("[FlameChat] " + message);
                break;

            case "warning":
                Bukkit.getLogger().warning("[FLameChat] " + message);
                break;

            case "severe":
                Bukkit.getLogger().severe("[FlameChat] " + message);
                break;
        }
    }

    /**
     * Gets the version of the plugin.
     * @return Version
     */
    protected static String getVersion()
    {
        return FlameChat.get().getDescription().getVersion();
    }

}