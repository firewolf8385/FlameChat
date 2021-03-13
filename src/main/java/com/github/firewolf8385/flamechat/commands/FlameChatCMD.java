package com.github.firewolf8385.flamechat.commands;

import com.github.firewolf8385.flamechat.Settings;
import com.github.firewolf8385.flamechat.utils.ChatUtils;
import org.bukkit.command.CommandSender;

/**
 * Main plugin command.
 */
public class FlameChatCMD extends AbstractCommand {
    private static final Settings settings = Settings.getInstance();

    /**
     * Registers the command.
     */
    public FlameChatCMD() {
        super("flamechat", "flamechat.admin", true);
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            return;
        }

        if(args[0].equals("reload")) {
            settings.reloadFormats();
            ChatUtils.chat(sender, "&a&l(&7!&a&l) &aChat formats have been reloaded!");
        }
    }
}