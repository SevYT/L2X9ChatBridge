package org.l2x9.betachatbridge.bukkitevents;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.l2x9.betachatbridge.BetaChatBridge;
import org.l2x9.betachatbridge.antispam.Cooldown;

import java.awt.*;

public class PlayerChat extends PlayerListener {
    BetaChatBridge plugin;

    public PlayerChat(BetaChatBridge betaChatBridge) {
        plugin = betaChatBridge;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        Cooldown cm = plugin.cm;
        Player player = event.getPlayer();
        if (cm.checkCooldown(player)) {
            cm.setCooldown(player, 3);
            sendEmbed(event.getMessage(), player.getName(), plugin.getChannel());
        } else {
            event.setCancelled(true);
        }
    }

    private void sendEmbed(String message, String playerName, TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setDescription("<" + playerName + "> " + message);
        embedBuilder.setColor(new Color(110, 255, 59));
        channel.sendMessage(embedBuilder.build()).queue();
    }
}