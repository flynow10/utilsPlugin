package com.wagologies.utilsplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandBase implements CommandExecutor {

    public abstract String getCommand();
}
