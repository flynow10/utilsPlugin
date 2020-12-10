package com.wagologies.utilsplugin.items;

import com.wagologies.utilsplugin.utils.gui.Item;
import com.wagologies.utilsplugin.utils.gui.pagenation.Pagenation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemsPreset {

    public ItemsPreset()
    {

        
        
        ItemStack stockOfStonks = new ItemStack(Material.PAPER);
        ItemMeta stockOfStonksMeta = stockOfStonks.getItemMeta();
        /*
        This is a test item for the Bazaar.
        Available from Warren for a limited time.

        Doesn't do anything for now.
        Might be worth 2x, 10x or 100x later on, or nothing.

        WARNING: RISKY INVESTMENT
        This is a TEST item, may get deleted!
         */
        stockOfStonksMeta.setDisplayName(ChatColor.RED + "Stock of Stonks");
        List<String> stockOfStonksLore = new ArrayList<>();
        stockOfStonksLore.add(ChatColor.RESET + "This is a test item for the Bazaar.");
        stockOfStonksLore.add("Available from Warren for a limited time.");
        stockOfStonksLore.add(ChatColor.RESET.toString() + "");
        stockOfStonksLore.add(ChatColor.RESET.toString() + "Doesn't do anything for now.");
        stockOfStonksLore.add(ChatColor.RESET.toString() + "Might be worth 2x, 10x or 100x later on, or nothing.");
        stockOfStonksLore.add(ChatColor.RESET.toString() + "");
        stockOfStonksLore.add(ChatColor.RESET.toString() + ChatColor.RED.toString() + "WARNING: RISKY INVESTMENT");
        stockOfStonksLore.add(ChatColor.RESET.toString() + "This is a TEST item, may get get deleted!");
        stockOfStonksMeta.setLore(stockOfStonksLore);
        stockOfStonks.setItemMeta(stockOfStonksMeta);
        //items.add(stockOfStonks);
    }

    public String getName() {
        return "Custom Items";
    }

    public int getSize() {
        return 54;
    }
    
}
