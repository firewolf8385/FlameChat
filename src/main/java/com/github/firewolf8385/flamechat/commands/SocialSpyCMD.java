package com.github.firewolf8385.flamechat.commands;

import com.github.firewolf8385.flamechat.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * View private messages.
 */
public class SocialSpyCMD extends AbstractCommand  {
    private static Collection<UUID> players = new HashSet<>();

    /**
     * Registers the command.
     */
    public SocialSpyCMD() {
        super("socialspy", "flameschat.socialspy", false);
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        if(getPlayers().contains(p.getUniqueId())) {
            getPlayers().remove(p.getUniqueId());
            ChatUtils.chat(p, "&a&l(&7!&a&l) &aYou are no longer spying on messages.");
        }
        else {
            getPlayers().add(p.getUniqueId());
            ChatUtils.chat(p, "&a&l(&7!&a&l) &aYou are now spying on messages.");
        }
    }

    public static Collection<UUID> getPlayers() {
        return players;
    }
}