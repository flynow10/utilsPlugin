package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.ban.BanGui;
import com.wagologies.utilsplugin.command.CommandBase;
import com.wagologies.utilsplugin.utils.gui.Gui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ban extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1)
            return false;
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Player banningPlayer = null;
            if (Bukkit.getPlayer(args[0]) != null) {
                banningPlayer = Bukkit.getPlayer(args[0]);
            }
            if(banningPlayer != null) {
                new Gui(player, new BanGui(banningPlayer));
                return true;
            }
            player.sendMessage(ChatColor.RED + "Couldn't find player");
        }
        sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
        return true;
    }

    @Override
    public String getCommand() {
        return "ban";
    }
}
