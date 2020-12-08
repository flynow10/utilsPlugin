package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Invsee extends CommandBase {
    @Override
    public String getCommand() {
        return "invsee";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 0)
            return false;
        if(commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            Player openingPlayer = Bukkit.getPlayer(args[0]);
            if(openingPlayer == null)
            {
                player.sendMessage(ChatColor.RED + "Couldn't find player");
                return true;
            }
            Inventory openingInventory = openingPlayer.getInventory();
            if(args.length > 1)
            {
                switch (args[1])
                {
                    case "main":
                        player.sendMessage(ChatColor.GREEN + "");
                        break;
                    case "ender":
                        openingInventory = openingPlayer.getEnderChest();
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "Invalid inventory type");
                        return false;
                }
            }
            player.openInventory(openingInventory);
            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
        return true;
    }
}
