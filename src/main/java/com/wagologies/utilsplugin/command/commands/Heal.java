package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal extends CommandBase {
    @Override
    public String getCommand() {
        return "heal";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 0)
        {
            if(commandSender instanceof Player)
                heal((Player) commandSender);
            else
                commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command without arguments!");
        }
        else
        {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player != null) {
                heal(player);
                commandSender.sendMessage(ChatColor.GREEN + "You healed " + ChatColor.BLUE + ChatColor.BOLD + player.getName());
            }
            else
                commandSender.sendMessage(ChatColor.RED + "Couldn't find player: " + ChatColor.BLUE + ChatColor.BOLD + strings[0]);
        }
        return true;
    }

    private void heal(Player player)
    {
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.sendMessage(ChatColor.GREEN + "You've been healed");
    }
}
