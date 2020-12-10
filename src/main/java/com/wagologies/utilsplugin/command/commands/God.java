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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class God extends CommandBase implements Listener {

    private List<Player> invinciblePlayers = new ArrayList<>();

    public God()
    {
        Bukkit.getPluginManager().registerEvents(this, UtilsPlugin.getInstance());
    }

    @Override
    public String getCommand() {
        return "god";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0) {
            if(commandSender instanceof Player)
                god((Player) commandSender);
            else
                commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command without arguments!");
        }
        else
        {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player != null) {
                god(player);
                commandSender.sendMessage(ChatColor.GREEN + "You toggled god for " + ChatColor.BLUE + ChatColor.BOLD + player.getName());
            }
            else
                commandSender.sendMessage(ChatColor.RED + "Couldn't find player: " + ChatColor.BLUE + ChatColor.BOLD + strings[0]);
        }
        return true;
    }

    private void god(Player player)
    {
        if(invinciblePlayers.contains(player)) {
            invinciblePlayers.remove(player);
            DisplayPackets.sendActionBar(player, "{text:\"" + ChatColor.RED + "You are now not invincible\"}");
        }
        else
        {
            invinciblePlayers.add(player);
            DisplayPackets.sendActionBar(player, "{text:\"" + ChatColor.GREEN + "You are now invincible\"}");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void damage(EntityDamageEvent event)
    {
        if(event.getEntity() instanceof Player)
        {
            if(invinciblePlayers.contains(event.getEntity()))
            {
                event.setDamage(0);
            }
        }
    }
}
