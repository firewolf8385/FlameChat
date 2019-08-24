package firewolf8385.flamechat.commands;

import firewolf8385.flamechat.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FC implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        // If the player does have have permissions, show plugin info.
        // Also runs if arguments there are no arguments
        if(!sender.hasPermission("flamechat.admin") || args.length == 0)
        {
            info(sender);
            return true;
        }

        switch(args[0])
        {
            default:
                help(sender);
                break;

            case "info":
                info(sender);
                break;

            case "reload":
                reload(sender);
                break;

            case "version":
                version(sender);
                break;
        }

        return true;
    }

    /**
     * Send plugin info to a sender
     * @param sender Command Sender
     */
    private void info(CommandSender sender)
    {
        Util.chat(sender, "&6&l]&8&m--------------------&6&lFlameChat&8&m--------------------&6&l[");
        Util.chat(sender, "  &8» &eAuthor &8- &ffirewolf8385");
        Util.chat(sender, "  &8» &eVersion &8- &f" + Util.getVersion());
        Util.chat(sender, "  &8» &eSpigot &8- &fComing soon.");
        Util.chat(sender, "&6&l]&8&m---------------------------------------------------&6&l[");
    }

    /**
     * Send plugin help page to a sender.
     * @param sender Command Sender
     */
    private void help(CommandSender sender)
    {
        Util.chat(sender, "&6&l]&8&m--------------------&6&lFlameChat&8&m--------------------&6&l[");
        Util.chat(sender, "  &8» &e/fc help &8- &fShows this page.");
        Util.chat(sender, "  &8» &e/fc info &8- &fShows plugin info");
        Util.chat(sender, "  &8» &e/fc reload &8- &fReloads config files.");
        Util.chat(sender, "  &8» &e/fc version &8- &fShows the plugin version.");
        Util.chat(sender, "&6&l]&8&m---------------------------------------------------&6&l[");
    }

    /**
     * Reload plugin configuration.
     * @param sender Command Sender
     */
    private void reload(CommandSender sender)
    {
        Util.chat(sender, "&6&l]&8&m--------------------&6&lFlameChat&8&m--------------------&6&l[");
        Util.chat(sender, "  &8» &eAuthor &8- &ffirewolf8385");
        Util.chat(sender, "  &8» &eVersion &8- &f" + Util.getVersion());
        Util.chat(sender, "  &8» &eSpigot &8- &fComing soon.");
        Util.chat(sender, "&6&l]&8&m---------------------------------------------------&6&l[");
    }

    /**
     * Show plugin version to a sender.
     * @param sender Command Sender
     */
    private void version(CommandSender sender)
    {
        Util.chat(sender, "&6&l]&8&m--------------------&6&lFlameChat&8&m--------------------&6&l[");
        Util.chat(sender, "  &8» &eVersion &8- &f" + Util.getVersion());
        Util.chat(sender, "&6&l]&8&m---------------------------------------------------&6&l[");
    }

}