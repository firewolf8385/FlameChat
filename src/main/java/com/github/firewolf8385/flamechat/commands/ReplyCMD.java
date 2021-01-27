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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReplyCMD implements CommandExecutor {
    private static final Settings settings = Settings.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cOnly players can message others.");
            return true;
        }

        if(args.length < 1) {
            ChatUtils.chat(sender, "&cIncorrect Usage! &7/r <message>");
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(MessageCMD.reply.get(player.getUniqueId()));

        if(target == null) {
            ChatUtils.chat(sender, "&c&l(&7!&c&l) &cThat player is not online.");
            return true;
        }

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length; i++) {
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

        MessageCMD.reply.put(player.getUniqueId(), target.getUniqueId());
        MessageCMD.reply.put(target.getUniqueId(), player.getUniqueId());

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

        return true;
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
