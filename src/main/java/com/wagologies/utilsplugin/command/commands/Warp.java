package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Warp extends CommandBase {
    @Override
    public String getCommand() {
        return "warp";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 1)
            return false;
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        if(Bukkit.getWorld(strings[0]) != null)
        {
            ((Player)commandSender).teleport(Bukkit.getWorld(strings[0]).getSpawnLocation());
        }
        return true;
    }
}
