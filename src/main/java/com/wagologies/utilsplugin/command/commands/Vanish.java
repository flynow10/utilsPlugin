package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.UtilsPlugin;
import com.wagologies.utilsplugin.command.CommandBase;
import com.wagologies.utilsplugin.utils.DisplayPackets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class Vanish extends CommandBase implements Listener {

    private List<Player> vanishedPlayers = new ArrayList<>();

    public Vanish()
    {
        Bukkit.getPluginManager().registerEvents(this, UtilsPlugin.getInstance());
    }

    @Override
    public String getCommand() {
        return "vanish";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 0) {
            if(commandSender instanceof Player)
                vanish((Player) commandSender);
            else
                commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command without arguments!");
        }
        else
        {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player != null) {
                vanish(player);
                commandSender.sendMessage(ChatColor.GREEN + "You toggled vanished for " + ChatColor.BLUE + ChatColor.BOLD + player.getName());
            }
            else
                commandSender.sendMessage(ChatColor.RED + "Couldn't find player: " + ChatColor.BLUE + ChatColor.BOLD + strings[0]);
        }
        return true;
    }

    private void vanish(Player player)
    {
        if(vanishedPlayers.contains(player)) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(!onlinePlayer.hasPermission("utils.seeVanished"))
                    onlinePlayer.showPlayer(player);
            }
            vanishedPlayers.remove(player);
            DisplayPackets.sendActionBar(player, "{text:\"" + ChatColor.RED + "You are now un-vanished\"}");
        }
        else
        {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if(!onlinePlayer.hasPermission("utils.seeVanished"))
                    onlinePlayer.hidePlayer(player);
            }
            vanishedPlayers.add(player);
            DisplayPackets.sendActionBar(player, "{text:\"" + ChatColor.GREEN + "You are now vanished\"}");
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event)
    {
        for (Player vanishedPlayer : vanishedPlayers) {
            if(!event.getPlayer().hasPermission("utils.seeVanished"))
                event.getPlayer().hidePlayer(vanishedPlayer);
        }
    }
    @EventHandler
    public void playerLeave(PlayerQuitEvent event)
    {
        if(vanishedPlayers.contains(event.getPlayer()))
        {
            vanishedPlayers.remove(event.getPlayer());
        }
        for (Player vanishedPlayer : vanishedPlayers) {
            if(!event.getPlayer().hasPermission("utils.seeVanished"))
                event.getPlayer().showPlayer(vanishedPlayer);
        }
    }
}
