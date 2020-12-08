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
        else
        {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player != null) {
                toggleFlight(player);
                commandSender.sendMessage(ChatColor.GREEN + "You toggled " + ChatColor.BLUE + ChatColor.BOLD + player.getName() + ChatColor.RESET + ChatColor.GREEN + "'s flight");
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
