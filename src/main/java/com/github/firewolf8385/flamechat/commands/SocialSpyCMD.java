package com.github.firewolf8385.flamechat.commands;

import com.github.firewolf8385.flamechat.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class SocialSpyCMD implements CommandExecutor {
    private static Collection<UUID> players = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cOnly players can use that command!!");
            return true;
        }

        if(!sender.hasPermission("flamechat.socialspy")) {
            ChatUtils.chat(sender, "&c&l(");
            return true;
        }

        Player p = (Player) sender;

        if(getPlayers().contains(p.getUniqueId())) {
            getPlayers().remove(p.getUniqueId());
            ChatUtils.chat(p, "&a&l(&7!&a&l) &aYou are no longer spying on messages.");
        }
        else {
            getPlayers().add(p.getUniqueId());
            ChatUtils.chat(p, "&a&l(&7!&a&l) &aYou are now spying on messages.");
        }

        return true;
    }

    public static Collection<UUID> getPlayers() {
        return players;
    }
}
