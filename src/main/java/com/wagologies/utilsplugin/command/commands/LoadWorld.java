package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;

public class LoadWorld extends CommandBase {
    @Override
    public String getCommand() {
        return "loadworld";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 1)
            return false;
        if(new File(Bukkit.getWorldContainer(),strings[0]).exists())
        {
            new WorldCreator(strings[0]).createWorld();
        }
        return true;
    }
}
