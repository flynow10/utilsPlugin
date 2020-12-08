package com.wagologies.utilsplugin;

import com.google.common.reflect.ClassPath;
import com.wagologies.utilsplugin.command.CommandBase;
import com.wagologies.utilsplugin.utils.GlowEnchantment;
import com.wagologies.utilsplugin.utils.gui.SignGUI;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.Field;

public final class UtilsPlugin extends JavaPlugin {

    private static UtilsPlugin instance;
    private SignGUI signGUI;

    @Override
    public void onEnable() {
        instance = this;
        registerCommands();
        signGUI = new SignGUI(this);
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
        cp.getTopLevelClassesRecursive("com.wagologies.utilsplugin.command.commands").forEach(classInfo -> {
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

    private void registerGlow()
    {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GlowEnchantment glow = new GlowEnchantment(70);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        instance = null;
        signGUI.destroy();
    }

    public static UtilsPlugin getInstance() { return instance; }
    public SignGUI getSignGUI() { return signGUI; }
}
