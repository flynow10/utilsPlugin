package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.command.CommandBase;
import com.wagologies.utilsplugin.items.ItemsPreset;
import com.wagologies.utilsplugin.utils.GlowEnchantment;
import com.wagologies.utilsplugin.utils.gui.Gui;
import com.wagologies.utilsplugin.utils.gui.Item;
import com.wagologies.utilsplugin.utils.gui.YesNo;
import com.wagologies.utilsplugin.utils.gui.pagenation.Page;
import com.wagologies.utilsplugin.utils.gui.pagenation.Pagenation;
import com.wagologies.utilsplugin.utils.gui.pagenation.Preset;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class Items extends CommandBase implements Listener {
    @Override
    public String getCommand() {
        return "items";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            new Gui(player, new Pagenation(new Preset() {
                @Override
                public String getName() {
                    return "Items";
                }

                @Override
                public int getSize() {
                    return 54;
                }

                @Override
                public List<Page> getPages() {
                    List<Page> pageList = new ArrayList<>();
                    HashMap<Function<Player, ItemStack>, Consumer<Player>> page1 = new LinkedHashMap<>();
                    HashMap<Function<Player, ItemStack>, Consumer<Player>> page2 = new LinkedHashMap<>();
                    ItemStack diamond32kSword = new ItemStack(Material.DIAMOND_SWORD);
                    ItemMeta diamond32kSwordMeta = diamond32kSword.getItemMeta();
                    diamond32kSwordMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "32K Sword");
                    diamond32kSwordMeta.spigot().setUnbreakable(true);
                    diamond32kSwordMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    diamond32kSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 32767, true);
                    diamond32kSword.setItemMeta(diamond32kSwordMeta);
                    page1.put(player1 -> diamond32kSword, player1 -> {
                        player1.getInventory().addItem(diamond32kSword);
                        player1.closeInventory();
                    });
                    page1.put(player1 -> {
                        ItemStack stack = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        ItemMeta stackMeta = stack.getItemMeta();
                        stackMeta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "Full Enchanted Diamond");
                        List<String> stackLore = new ArrayList<>();
                        stackLore.add(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "HELMET:");
                        stackLore.add(ChatColor.AQUA.toString() + "Protection IV");
                        stackLore.add(ChatColor.AQUA.toString() + "Unbreaking III");
                        stackLore.add(ChatColor.AQUA.toString() + "Thorns III");
                        stackLore.add(ChatColor.AQUA.toString() + "Aqua Affinity I");
                        stackLore.add(ChatColor.AQUA.toString() + "Respiration III");
                        stackLore.add(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "CHESTPLATE:");
                        stackLore.add(ChatColor.AQUA.toString() + "Protection IV");
                        stackLore.add(ChatColor.AQUA.toString() + "Unbreaking III");
                        stackLore.add(ChatColor.AQUA.toString() + "Thorns III");
                        stackLore.add(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "LEGGINGS:");
                        stackLore.add(ChatColor.AQUA.toString() + "Protection IV");
                        stackLore.add(ChatColor.AQUA.toString() + "Unbreaking III");
                        stackLore.add(ChatColor.AQUA.toString() + "Thorns III");
                        stackLore.add(ChatColor.BLUE.toString() + ChatColor.BOLD.toString() + "BOOTS:");
                        stackLore.add(ChatColor.AQUA.toString() + "Protection IV");
                        stackLore.add(ChatColor.AQUA.toString() + "Unbreaking III");
                        stackLore.add(ChatColor.AQUA.toString() + "Thorns III");
                        stackLore.add(ChatColor.AQUA.toString() + "Depth Strider III");
                        stackLore.add(ChatColor.AQUA.toString() + "Feather Falling IV");
                        stackMeta.setLore(stackLore);
                        stack.setItemMeta(stackMeta);
                        stack = GlowEnchantment.ApplyGlow(stack);
                        return stack;
                    }, player1 -> new Gui(player1, new YesNo("Unbreakable", player2 -> {
                        ItemStack[] armor = new ItemStack[4];
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                        Map<Enchantment, Integer> helmetEnchants = new HashMap<>();
                        helmetEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        helmetEnchants.put(Enchantment.DURABILITY, 3);
                        helmetEnchants.put(Enchantment.THORNS, 3);
                        helmetEnchants.put(Enchantment.WATER_WORKER, 1);
                        helmetEnchants.put(Enchantment.OXYGEN,3);
                        helmet.addEnchantments(helmetEnchants);
                        ItemMeta helmetMeta = helmet.getItemMeta();
                        helmetMeta.spigot().setUnbreakable(true);
                        helmetMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        helmet.setItemMeta(helmetMeta);
                        armor[3] = helmet;
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        Map<Enchantment, Integer> chestplateEnchants = new HashMap<>();
                        chestplateEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        chestplateEnchants.put(Enchantment.DURABILITY, 3);
                        chestplateEnchants.put(Enchantment.THORNS, 3);
                        chestplate.addEnchantments(chestplateEnchants);
                        ItemMeta chestplateMeta = chestplate.getItemMeta();
                        chestplateMeta.spigot().setUnbreakable(true);
                        chestplateMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        chestplate.setItemMeta(chestplateMeta);
                        armor[2] = chestplate;
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                        Map<Enchantment, Integer> leggingEnchants = new HashMap<>();
                        leggingEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        leggingEnchants.put(Enchantment.DURABILITY, 3);
                        leggingEnchants.put(Enchantment.THORNS, 3);
                        leggings.addEnchantments(leggingEnchants);
                        ItemMeta leggingMeta = leggings.getItemMeta();
                        leggingMeta.spigot().setUnbreakable(true);
                        leggingMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        leggings.setItemMeta(leggingMeta);
                        armor[1] = leggings;
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                        Map<Enchantment, Integer> bootsEnchants = new HashMap<>();
                        bootsEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        bootsEnchants.put(Enchantment.DURABILITY, 3);
                        bootsEnchants.put(Enchantment.THORNS, 3);
                        bootsEnchants.put(Enchantment.DEPTH_STRIDER, 3);
                        bootsEnchants.put(Enchantment.PROTECTION_FALL, 4);
                        boots.addEnchantments(bootsEnchants);
                        ItemMeta bootsMeta = boots.getItemMeta();
                        bootsMeta.spigot().setUnbreakable(true);
                        bootsMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                        boots.setItemMeta(bootsMeta);
                        armor[0] = boots;
                        player2.getInventory().setArmorContents(armor);
                        player2.closeInventory();
                    }, player2 -> {
                        ItemStack[] armor = new ItemStack[4];
                        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                        Map<Enchantment, Integer> helmetEnchants = new HashMap<>();
                        helmetEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        helmetEnchants.put(Enchantment.DURABILITY, 3);
                        helmetEnchants.put(Enchantment.THORNS, 3);
                        helmetEnchants.put(Enchantment.WATER_WORKER, 1);
                        helmetEnchants.put(Enchantment.OXYGEN,3);
                        helmet.addEnchantments(helmetEnchants);
                        armor[3] = helmet;
                        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                        Map<Enchantment, Integer> chestplateEnchants = new HashMap<>();
                        chestplateEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        chestplateEnchants.put(Enchantment.DURABILITY, 3);
                        chestplateEnchants.put(Enchantment.THORNS, 3);
                        chestplate.addEnchantments(chestplateEnchants);
                        armor[2] = chestplate;
                        ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                        Map<Enchantment, Integer> leggingEnchants = new HashMap<>();
                        leggingEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        leggingEnchants.put(Enchantment.DURABILITY, 3);
                        leggingEnchants.put(Enchantment.THORNS, 3);
                        leggings.addEnchantments(leggingEnchants);
                        armor[1] = leggings;
                        ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                        Map<Enchantment, Integer> bootsEnchants = new HashMap<>();
                        bootsEnchants.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                        bootsEnchants.put(Enchantment.DURABILITY, 3);
                        bootsEnchants.put(Enchantment.THORNS, 3);
                        bootsEnchants.put(Enchantment.DEPTH_STRIDER, 3);
                        bootsEnchants.put(Enchantment.PROTECTION_FALL, 4);
                        boots.addEnchantments(bootsEnchants);
                        armor[0] = boots;
                        player2.getInventory().setArmorContents(armor);
                        player2.closeInventory();
                    })));
                    ItemStack stockOfStonks = new ItemStack(Material.PAPER);
                    ItemMeta stockOfStonksMeta = stockOfStonks.getItemMeta();
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
                    page2.put(player1 -> stockOfStonks, player1 -> {
                        player1.getInventory().addItem(stockOfStonks);
                        player1.closeInventory();
                    });
                    pageList.add(new Page(page1));
                    pageList.add(new Page(page2));
                    return pageList;
                }

                @Override
                public List<Item> getExtraItems() {
                    return null;
                }
            }));
            return true;
        }
        commandSender.sendMessage(ChatColor.RED + "You must be a player to use this command");
        return true;
    }
}
