package com.wagologies.utilsplugin.command.commands;

import com.wagologies.utilsplugin.UtilsPlugin;
import com.wagologies.utilsplugin.command.CommandBase;
import com.wagologies.utilsplugin.pvp.AddPvpRule;
import com.wagologies.utilsplugin.pvp.PvpRule;
import com.wagologies.utilsplugin.utils.GlowEnchantment;
import com.wagologies.utilsplugin.utils.gui.Gui;
import com.wagologies.utilsplugin.utils.gui.Item;
import com.wagologies.utilsplugin.utils.gui.Preset;
import com.wagologies.utilsplugin.utils.gui.pagenation.Page;
import com.wagologies.utilsplugin.utils.gui.pagenation.Pagenation;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Pvp extends CommandBase implements Listener {

    public List<PvpRule> pvpRules = new ArrayList<>();

    public Pvp()
    {
        Bukkit.getPluginManager().registerEvents(this, UtilsPlugin.getInstance());
    }

    @Override
    public String getCommand() {
        return "pvp";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player))
            return true;
        PvpMenu((Player) commandSender);
        return true;
    }

    public void PvpMenu(Player player)
    {
        new Gui(player, new Preset() {
            @Override
            public String getName() {
                return "Pvp Config";
            }

            @Override
            public int getSize() {
                return 27;
            }

            @Override
            public List<Item> getItems() {
                List<Item> items = new ArrayList<>();
                items.add(new Item(12, player -> {
                    ItemStack stack = new ItemStack(Material.PAPER);
                    ItemMeta stackMeta = stack.getItemMeta();
                    stackMeta.setDisplayName("List " + ChatColor.GREEN.toString() + "PVP" + ChatColor.AQUA.toString() +" rules");
                    stack.setItemMeta(stackMeta);
                    GlowEnchantment.ApplyGlow(stack);
                    return stack;
                }, Pvp.this::PvpRules));
                items.add(new Item(14, player1 -> {
                    ItemStack stack = new ItemStack(Material.IRON_SWORD);
                    ItemMeta stackMeta = stack.getItemMeta();
                    stackMeta.setDisplayName("Add" + ChatColor.GREEN.toString() + " PVP" + ChatColor.AQUA.toString() + " rule");
                    stack.setItemMeta(stackMeta);
                    GlowEnchantment.ApplyGlow(stack);
                    return stack;
                }, Pvp.this::PvpAdd));
                return items;
            }
        });
    }

    public void PvpRules(Player player)
    {
        new Gui(player, new Pagenation(new com.wagologies.utilsplugin.utils.gui.pagenation.Preset() {
            @Override
            public String getName() {
                return "PVP Rules";
            }

            @Override
            public int getSize() {
                return 36;
            }

            @Override
            public List<Page> getPages() {
                List<Page> pages = new ArrayList<>();
                int pageCount = Math.floorDiv(pvpRules.size(),14) + 1;
                for (int i = 0; i < pageCount; i++) {
                    LinkedHashMap<Function<Player, ItemStack>, Consumer<Player>> items = new LinkedHashMap<>();
                    for (int j = i*14; j < Math.min((i*14) + 14, pvpRules.size()); j++) {
                        PvpRule rule = pvpRules.get(j);
                        items.put(player1 -> {
                            ItemStack stack = new ItemStack(Material.MAP);
                            ItemMeta stackMeta = stack.getItemMeta();
                            stackMeta.setDisplayName((rule.isPvpOn() ? ChatColor.GREEN.toString() : ChatColor.RED.toString()) + rule.getName());
                            List<String> lore = new ArrayList<>();
                            lore.add(ChatColor.AQUA.toString() + "Type: " + ChatColor.WHITE.toString() + rule.getType().name());
                            lore.add("");
                            lore.add(ChatColor.AQUA.toString() + "Value: " + (rule.isPvpOn() ? ChatColor.GREEN.toString() + "ON" : ChatColor.RED.toString() + "OFF"));
                            lore.add("");
                            lore.add(ChatColor.RED.toString() + "Click to delete");
                            stackMeta.setLore(lore);
                            stack.setItemMeta(stackMeta);
                            GlowEnchantment.ApplyGlow(stack);
                            return stack;
                        }, player1 -> {
                            pvpRules.remove(rule);
                        });
                    }
                    pages.add(new Page(items));
                }
                return pages;
            }

            @Override
            public List<Item> getExtraItems() {
                List<Item> items = new ArrayList<>();
                items.add(new Item(31, player1 -> {
                    ItemStack stack = new Wool(DyeColor.RED).toItemStack(1);
                    ItemMeta itemMeta = stack.getItemMeta();
                    itemMeta.setDisplayName(ChatColor.RED.toString() + "Back");
                    stack.setItemMeta(itemMeta);
                    return stack;
                }, Pvp.this::PvpMenu));
                return items;
            }
        }));
    }

    public void PvpAdd(Player player)
    {
        AddPvpRule ruleMenu = new AddPvpRule(this);
        new Gui(player, ruleMenu);
    }

    @EventHandler
    public void onEntityAttack(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getDamager();
            World world = player.getWorld();
            boolean damage = true;
            List<PvpRule> worldRules = pvpRules.stream().filter(pvpRule -> pvpRule.getType() == PvpRule.PvpType.WORLD).collect(Collectors.toList());
            List<PvpRule> playerRules = pvpRules.stream().filter(pvpRule -> pvpRule.getType() == PvpRule.PvpType.PLAYER).collect(Collectors.toList());
            for (PvpRule worldRule : worldRules) {
                if(worldRule.getName().equals(world.getName()))
                {
                    damage = worldRule.isPvpOn();
                }
            }
            for (PvpRule playerRule : playerRules) {
                if(playerRule.getName().equals(player.getName()))
                {
                    damage = playerRule.isPvpOn();
                }
            }
            if(event.getCause() == EntityDamageEvent.DamageCause.THORNS)
                damage = true;
            event.setCancelled(!damage);
        }
    }
}
