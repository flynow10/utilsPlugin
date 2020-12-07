package com.wagologies.utilsplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Ban extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1)
            return false;
        if(Bukkit.getPlayer(args[0]) != null)
        {

        }
        return true;
    }

    @Override
    public String getCommand() {
        return "ban";
    }
}
