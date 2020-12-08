package com.wagologies.utilsplugin.utils.gui;

import com.wagologies.utilsplugin.UtilsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class Gui implements Listener {

    private Inventory inventory;
    private Player player;
    private Preset preset;
    private HashMap<Integer, Item> menuItems = new HashMap<>();

    public Gui(Player player, Preset preset)
    {
        this.player = player;
        this.preset = preset;
        inventory = Bukkit.createInventory(null, preset.getSize(), preset.getName());
        Bukkit.getPluginManager().registerEvents(this, UtilsPlugin.getInstance());
        SetUpInventory();
        OpenMenu();
    }

    private void SetUpInventory()
    {
        inventory.clear();
        for (Item item : preset.getItems()) {
            if(item.slot < preset.getSize() && inventory.getItem(item.slot) == null || inventory.getItem(item.slot).getType().equals(Material.AIR))
            {
                menuItems.put(item.slot, item);
                inventory.setItem(item.slot, item.placeHolder.apply(player));
            }
            else
            {
                player.closeInventory();
                player.sendMessage("Something went wrong when creating the inventory");
            }
        }
        for (int i = 0; i < inventory.getSize(); i++)
        {
            if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR))
            {
                inventory.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE));
            }
        }
    }
    public void OpenMenu()
    {
        player.openInventory(inventory);
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent event)
    {
        if(!event.getInventory().equals(inventory))
            return;

        if(!event.isCancelled())
            event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        if(clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if(menuItems.containsKey(event.getSlot()))
        {
            menuItems.get(event.getSlot()).callback.accept(player);
            SetUpInventory();
        }
    }

    @EventHandler
    public void DragEvent(InventoryDragEvent event)
    {
        if(event.getInventory().equals(inventory))
            if(!event.isCancelled())
                event.setCancelled(true);
    }
}
