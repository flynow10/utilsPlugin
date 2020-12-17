package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.command.CommandBase;
import com.wagologies.utilsplugin.utils.DisplayPackets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly extends CommandBase {
    @Override
    public String getCommand() {
        return "fly";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 0) {
            if(commandSender instanceof Player) {
                Player player = (Player) commandSender;
                toggleFlight(player);
            }
            else
                commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command without arguments!");
        }
        else if(strings.length == 1)
        {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player != null) {
                toggleFlight(player);
                commandSender.sendMessage(ChatColor.GREEN + "You toggled " + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + ChatColor.GREEN + "'s flight");
            }
            else
                commandSender.sendMessage(ChatColor.RED + "Couldn't find player: " + ChatColor.BLUE + ChatColor.BOLD + strings[0]);
        }
        else if(strings.length == 2)
        {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player != null) {
                float parsedInt = 1;
                try
                {
                    parsedInt = Float.parseFloat(strings[1]);
                }
                catch (NumberFormatException e)
                {
                    player.sendMessage(ChatColor.RED.toString() + "Please enter a number");
                    return true;
                }
                player.setFlySpeed(Math.max(Math.min(parsedInt, 10), -10)/10f);
                player.sendMessage(ChatColor.GREEN + "You're fly speed is now " + ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + parsedInt);
            }
            else
                commandSender.sendMessage(ChatColor.RED + "Couldn't find player: " + ChatColor.BLUE + ChatColor.BOLD + strings[0]);
        }
        return true;
    }

    private void toggleFlight(Player player)
    {
        player.setAllowFlight(!player.getAllowFlight());
        DisplayPackets.sendActionBar(player, "{text: \"" + (player.getAllowFlight() ? ChatColor.GREEN : ChatColor.RED) + "You can now " + (player.getAllowFlight() ? "fly" : "not fly") + "\"}");
    }
}
