package com.wagologies.utilsplugin;

import com.google.common.reflect.ClassPath;
import com.wagologies.utilsplugin.commands.CommandBase;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class UtilsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
    }

    private void registerCommands()
    {
        ClassPath cp = null;
        try {
            cp = ClassPath.from(getClass().getClassLoader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert cp != null;
        cp.getTopLevelClassesRecursive("com.wagologies.utilsplugin.commands").forEach(classInfo -> {
            Class commandClass;
            try {
                commandClass = Class.forName(classInfo.getName());
                Object commandObject = commandClass.newInstance();
                if (commandObject instanceof CommandBase) {
                    CommandBase cmd = (CommandBase) commandObject;
                    getCommand(cmd.getCommand()).setExecutor(cmd);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
