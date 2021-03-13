package com.github.firewolf8385.flamechat.commands;

import com.github.firewolf8385.flamechat.Settings;
import com.github.firewolf8385.flamechat.utils.ChatUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Message another player.
 */
public class MessageCMD extends AbstractCommand implements CommandExecutor {
    private static final Settings settings = Settings.getInstance();
    public static Map<UUID, UUID> reply = new HashMap<>();

    /**
     * Registers the command.
     */
    public MessageCMD() {
        super("message", "flamechat.message", false);
    }

    /**
     * Executes the command.
     * @param sender The Command Sender.
     * @param args Arguments of the command.
     */
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cUsage: /msg <player> <message>");
            return;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cThat player is not online.");
            return;
        }

        StringBuilder message = new StringBuilder();

        for (int i = 1; i < args.length; i++) {
            message.append(args[i]);

            if(i + 1 != args.length) {
                message.append(" ");
            }
        }

        TextComponent playerPrefix = makeComponent(player, settings.getConfig(), "private_message_formats.to-sender.format", player, target);
        BaseComponent[] playerMSG = new ComponentBuilder()
                .append(playerPrefix)
                .append(TextComponent.fromLegacyText(ChatUtils.translate(settings.getConfig().getString("private_message_formats.to-sender.message-color") + message)))
                .create();
        player.spigot().sendMessage(playerMSG);

        TextComponent targetPrefix = makeComponent(player, settings.getConfig(), "private_message_formats.to-recipient.format", player, target);
        BaseComponent[] targetMSG = new ComponentBuilder()
                .append(targetPrefix)
                .append(TextComponent.fromLegacyText(ChatUtils.translate(settings.getConfig().getString("private_message_formats.to-recipient.message-color") + message)))
                .create();
        target.spigot().sendMessage(targetMSG);

        reply.put(player.getUniqueId(), target.getUniqueId());
        reply.put(target.getUniqueId(), player.getUniqueId());

        for(UUID u : SocialSpyCMD.getPlayers()) {
            Player spy = Bukkit.getPlayer(u);

            if(spy == null) {
                SocialSpyCMD.getPlayers().remove(u);
                continue;
            }

            if(player.getUniqueId() == u || target.getUniqueId() == u) {
                continue;
            }

            ChatUtils.chat(spy, settings.getConfig().getString("private_message_formats.social-spy").replace("%player%", player.getName()).replace("%recipient%", target.getName()) + message);
        }
    }


    private TextComponent makeComponent(Player p, FileConfiguration config, String path, Player player, Player target) {
        String str = PlaceholderAPI.setPlaceholders(p, config.getString(path + ".text").replace("%player_name%", player.getName()).replace("%recipient_player_name%", target.getName()));
        TextComponent com = new TextComponent(TextComponent.fromLegacyText(ChatUtils.translate(str)));
        com.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, PlaceholderAPI.setPlaceholders(p, config.getString(path + ".click-command"))));

        List<String> lines = config.getStringList(path + ".tooltip");
        ArrayList components = new ArrayList();
        TextComponent hoverMessage = new TextComponent(new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, ChatUtils.translate(lines.get(0)))).create());
        TextComponent newLine = new TextComponent(ComponentSerializer.parse("{text: \"\n\"}"));
        for(int i = 1; i < lines.size(); i++){
            hoverMessage.addExtra(newLine);
            hoverMessage.addExtra(new TextComponent(new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, ChatUtils.translate(lines.get(i)))).create()));
        }
        components.add(hoverMessage);
        BaseComponent[] hoverToSend = (BaseComponent[])components.toArray(new BaseComponent[components.size()]);

        com.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverToSend));

        return com;
    }
}
