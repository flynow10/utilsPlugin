package com.wagologies.utilsplugin.pvp;

import com.wagologies.utilsplugin.UtilsPlugin;
import com.wagologies.utilsplugin.command.commands.Pvp;
import com.wagologies.utilsplugin.utils.GlowEnchantment;
import com.wagologies.utilsplugin.utils.gui.Gui;
import com.wagologies.utilsplugin.utils.gui.Item;
import com.wagologies.utilsplugin.utils.gui.Preset;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.Wool;

import java.util.ArrayList;
import java.util.List;

public class AddPvpRule implements Preset {

    String currentName;
    PvpRule.PvpType currentType = PvpRule.PvpType.PLAYER;
    boolean isPvpOn = false;
    Pvp pvp;

    public AddPvpRule(Pvp pvp)
    {
        this.pvp = pvp;
    }

    @Override
    public String getName() {
        return "New PVP rule";
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        items.add(new Item(11, player -> {
            ItemStack stack = new ItemStack(Material.PAPER);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(ChatColor.AQUA.toString() + (currentType == PvpRule.PvpType.PLAYER ? "Player" : "World" ) + " name");
            if(currentName != null)
            {
                stackMeta.setDisplayName((currentType == PvpRule.PvpType.PLAYER ? "Player" : "World" ) + " name: " + ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + currentName);
            }
            stack.setItemMeta(stackMeta);
            if(currentName != null)
                GlowEnchantment.ApplyGlow(stack);
            return stack;
        }, this::PickNewName));
        items.add(new Item(13, player -> {
            ItemStack stack = new Dye(isPvpOn ? DyeColor.LIME : DyeColor.GRAY).toItemStack(1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName("Toggled: " + (isPvpOn ? ChatColor.GREEN.toString() + "ON" : ChatColor.RED.toString() + "OFF"));
            stack.setItemMeta(stackMeta);
            GlowEnchantment.ApplyGlow(stack);
            return stack;
        }, player -> isPvpOn = !isPvpOn));
        items.add(new Item(15, player -> {
            ItemStack stack;
            if(currentType == PvpRule.PvpType.PLAYER)
            {
                stack = new ItemStack(Material.GOLD_INGOT);
            }
            else if (currentType == PvpRule.PvpType.WORLD)
            {
                stack = new ItemStack(Material.GOLD_BLOCK);
            }
            else
            {
                stack = new ItemStack(Material.BARRIER);
                ItemMeta barrierMeta = stack.getItemMeta();
                barrierMeta.setDisplayName("Unknown Type!");
                stack.setItemMeta(barrierMeta);
                GlowEnchantment.ApplyGlow(stack);
                return stack;
            }
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(ChatColor.GREEN.toString() + "Current Type: " + ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + currentType.name());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.RESET.toString() + ChatColor.YELLOW.toString() + ChatColor.BOLD.toString() + "Available Types");
            lore.add(ChatColor.RESET.toString() + (currentType == PvpRule.PvpType.WORLD ? ChatColor.GREEN.toString() : ChatColor.GRAY.toString()) + "World");
            lore.add(ChatColor.RESET.toString() + (currentType == PvpRule.PvpType.PLAYER ? ChatColor.GREEN.toString() : ChatColor.GRAY.toString()) + "Player");
            stackMeta.setLore(lore);
            stack.setItemMeta(stackMeta);
            GlowEnchantment.ApplyGlow(stack);
            return stack;
        }, player -> currentType = currentType.next()));
        items.add(new Item(31, player -> {
            ItemStack stack = new Wool(currentName != null ? DyeColor.GREEN : DyeColor.RED).toItemStack(1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(currentName != null ? ChatColor.GREEN.toString() + "Create Rule" : ChatColor.RED.toString() + "Name not set!");
            stack.setItemMeta(stackMeta);
            return stack;
        }, this::AddRule));
        return items;
    }

    public void PickNewName(Player player)
    {
        UtilsPlugin.getInstance().getSignGUI().open(player, new String[] {"", "^^^^", "Type name", "here"}, (player1, lines) -> {
            if(lines[0].substring(1, lines[0].length()-1).length() == 0) {
                this.currentName = null;
            }
            this.currentName = lines[0].substring(1, lines[0].length()-1);
            new Gui(player1, this);
        });
    }
    public void AddRule(Player player)
    {
        if(currentName != null)
        {
            pvp.pvpRules.add(new PvpRule(currentName, currentType, isPvpOn));
            pvp.PvpMenu(player);
        }
    }
}
