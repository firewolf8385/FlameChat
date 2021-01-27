package com.github.firewolf8385.flamechat.commands;

import com.github.firewolf8385.flamechat.Settings;
import com.github.firewolf8385.flamechat.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FlameChatCMD implements CommandExecutor {
    private static final Settings settings = Settings.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("flamechat.admin")) {
            return true;
        }

        if(args.length == 0) {
            return true;
        }

        if(args[0].equals("reload")) {
            settings.reloadFormats();
            ChatUtils.chat(sender, "&a&l(&7!&a&l) &aChat formats have been reloaded!");
        }

        return true;
    }
}