package com.github.firewolf8385.flamechat.listeners;

import com.github.firewolf8385.flamechat.Settings;
import com.github.firewolf8385.flamechat.utils.ChatUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerChatListener implements Listener
{
    private static final Settings settings = Settings.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if(e.isCancelled()) {
            return;
        }

        Player p = e.getPlayer();

        if(!p.hasPermission("flamechat.color")) {
            e.setMessage(ChatUtils.translate(e.getMessage()));
            e.setMessage(ChatColor.stripColor(e.getMessage()));
        }

        e.getRecipients().clear();

        String format = "default";
        for(String str : settings.getFormats().getConfigurationSection("formats").getKeys(false)) {
            if(p.hasPermission("format." + str)) {
                format = str;
                break;
            }
        }

        TextComponent prefix = makeComponent(p, "formats." + format + ".prefix");
        TextComponent name = makeComponent(p, "formats." + format + ".name");
        TextComponent suffix = makeComponent(p, "formats." + format + ".suffix");

        BaseComponent[] chat = new ComponentBuilder()
                .append(prefix)
                .append(name)
                .append(suffix)
                .append(TextComponent.fromLegacyText(ChatUtils.translate(PlaceholderAPI.setPlaceholders(p, settings.getFormats().getString("formats." + format + ".message-color")) + e.getMessage())))
                .create();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getName().contains("*")) {
                player.sendMessage(TextComponent.toLegacyText(chat));
            }
            else {
                player.spigot().sendMessage(chat);
            }
        }

    }

    private TextComponent makeComponent(Player p, String path) {
        String str = PlaceholderAPI.setPlaceholders(p, settings.getFormats().getString(path + ".text"));
        TextComponent com = new TextComponent(TextComponent.fromLegacyText(ChatUtils.translate(str)));
        com.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, PlaceholderAPI.setPlaceholders(p, settings.getFormats().getString(path + ".click-command"))));

        List<String> lines = settings.getFormats().getStringList(path + ".tooltip");
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