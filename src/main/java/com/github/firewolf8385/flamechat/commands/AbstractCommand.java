package com.github.firewolf8385.flamechat.commands;

import com.github.firewolf8385.flamechat.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractCommand implements CommandExecutor {
    private final String commandName;
    private final String permission;
    private final boolean canConsoleUse;
    private static JavaPlugin plugin;

    /**
     * Creates a new AbstractCommand.
     * @param commandName Name of the command.
     * @param permission Permission required to use the command.
     * @param canConsoleUse Whether or not console can use the command.
     */
    public AbstractCommand(final String commandName, final String permission, final boolean canConsoleUse) {
        this.commandName = commandName;
        this.permission = permission;
        this.canConsoleUse = canConsoleUse;
        plugin.getCommand(commandName).setExecutor(this);
    }

    /**
     * Registers all commands in the plugin.
     * @param pl Plugin.
     */
    public static void registerCommands(JavaPlugin pl) {
        plugin = pl;

        new FlameChatCMD();
        new MessageCMD();
        new ReplyCMD();
        new SocialSpyCMD();
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Processes early execution of the plugin.
     * @param sender Command Sender.
     * @param cmd The Command.
     * @param label Command Label.
     * @param args Command Arugments.
     * @return Successful Completion.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!cmd.getLabel().equalsIgnoreCase(commandName))
            return true;
        if(!sender.hasPermission(permission)){
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cYou do not have access to that command.");
            return true;
        }
        if(!canConsoleUse && !(sender instanceof Player)){
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cOnly players can use that command.");
            return true;
        }
        execute(sender, args);
        return true;
    }
}