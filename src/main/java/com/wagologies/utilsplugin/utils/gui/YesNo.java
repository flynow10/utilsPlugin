package com.wagologies.utilsplugin.utils.gui;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class YesNo implements Preset {

    String question;
    Consumer<Player> yes;
    Consumer<Player> no;

    public YesNo(String question, Consumer<Player> yes, Consumer<Player> no)
    {
        this.question = question;
        this.yes = yes;
        this.no = no;
    }

    @Override
    public String getName() {
        return question;
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(11, player -> {
            ItemStack stack = new Wool(DyeColor.GREEN).toItemStack(1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(ChatColor.GREEN + "Yes!");
            stack.setItemMeta(stackMeta);
            return stack;
        }, yes));
        items.add(new Item(15, player -> {
            ItemStack stack = new Wool(DyeColor.RED).toItemStack(1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(ChatColor.RED + "No!");
            stack.setItemMeta(stackMeta);
            return stack;
        }, no));
        return items;
    }
}
