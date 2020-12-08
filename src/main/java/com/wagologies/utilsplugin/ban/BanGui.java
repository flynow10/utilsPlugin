package com.wagologies.utilsplugin.ban;

import com.wagologies.utilsplugin.UtilsPlugin;
import com.wagologies.utilsplugin.utils.gui.Item;
import com.wagologies.utilsplugin.utils.gui.Preset;
import com.wagologies.utilsplugin.utils.gui.SignGUI;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Dye;
import org.bukkit.material.Wool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BanGui implements Preset {

    private Player banningPlayer;
    private boolean shouldSetReason = false;

    public BanGui(Player banningPlayer)
    {
        this.banningPlayer = banningPlayer;
    }

    @Override
    public String getName() {
        return "Ban " + banningPlayer.getName();
    }

    @Override
    public int getSize() {
        return 36;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < Integer.min(Times.values().length,7); i++) {
            final Times times = Times.values()[i];
            items.add(new Item(i+10, player -> {
                ItemStack stack = new Wool(times.color).toItemStack(1);
                ItemMeta stackMeta = stack.getItemMeta();
                stackMeta.setDisplayName("Ban for " + times.name);
                stack.setItemMeta(stackMeta);
                return stack;
            }, player -> {
                String bumper = StringUtils.repeat("\n", 35);
                player.closeInventory();
                if(shouldSetReason)
                {
                    UtilsPlugin.getInstance().getSignGUI().open(player, new SignGUI.SignGUIListener() {
                        @Override
                        public void onSignDone(Player player, String[] lines) {
                            String reason = lines[0].substring(1, lines[0].length()-1) + lines[1].substring(1, lines[1].length()-1) + lines[2].substring(1, lines[2].length()-1) + lines[3].substring(1, lines[3].length()-1);
                            Date time;
                            if (times.miliseconds == Long.MAX_VALUE) {
                                time = null;
                                reason += "\nTime: Forever";
                            } else {
                                time = new Date(System.currentTimeMillis() + times.miliseconds);
                                reason += "\nTime: " + time.toString();
                            }
                            reason = bumper + reason + bumper;
                            Bukkit.getBanList(BanList.Type.NAME).addBan(banningPlayer.getName(), reason, time, null);
                            banningPlayer.kickPlayer(reason);
                        }
                    });
                }
                else {
                    String reason = "You've been banned";
                    Date time;
                    if (times.miliseconds == Long.MAX_VALUE) {
                        time = null;
                        reason += "\nTime: Forever";
                    } else {
                        time = new Date(System.currentTimeMillis() + times.miliseconds);
                        reason += "\nTime: " + time.toString();
                    }
                    reason = bumper + reason + bumper;
                    Bukkit.getBanList(BanList.Type.NAME).addBan(banningPlayer.getName(), reason, time, null);
                    banningPlayer.kickPlayer(reason);
                }
            }));
        }
        items.add(new Item(31, player -> {
            ItemStack stack = new Wool(DyeColor.RED).toItemStack(1);
            ItemMeta stackMeta = stack.getItemMeta();
            stackMeta.setDisplayName(ChatColor.RED + "Cancel");
            stack.setItemMeta(stackMeta);
            return stack;
        }, HumanEntity::closeInventory));
        items.add(new Item(35, player -> {
            if(shouldSetReason) {
                ItemStack stack = new Dye(DyeColor.LIME).toItemStack(1);
                ItemMeta stackMeta = stack.getItemMeta();
                stackMeta.setDisplayName("Default Reason");
                stack.setItemMeta(stackMeta);
                return stack;
            }
            else
            {
                ItemStack stack = new Dye(DyeColor.GRAY).toItemStack(1);
                ItemMeta stackMeta = stack.getItemMeta();
                stackMeta.setDisplayName("Custom Reason");
                stack.setItemMeta(stackMeta);
                return stack;
            }
        }, player -> {
            shouldSetReason = !shouldSetReason;
        }));
        return items;
    }
}
enum Times
{
    MINUTE(60L*1000, "1 Minute", DyeColor.RED),
    HOUR(60L*60*1000, "1 Hour", DyeColor.YELLOW),
    DAY(24L*60*60*1000, "1 Day", DyeColor.ORANGE),
    WEEK(7L*24*60*60*1000, "1 Week", DyeColor.GREEN),
    MONTH(31L*24*60*60*1000, "1 Month", DyeColor.LIGHT_BLUE),
    YEAR(365L*24*60*60*1000, "1 Year", DyeColor.BLUE),
    FOREVER(Long.MAX_VALUE, "Forever", DyeColor.PURPLE);

    public long miliseconds;
    public String name;
    public DyeColor color;

    Times(long miliseconds, String name, DyeColor color)
    {
        this.miliseconds = miliseconds;
        this.name = name;
        this.color = color;
    }
}
