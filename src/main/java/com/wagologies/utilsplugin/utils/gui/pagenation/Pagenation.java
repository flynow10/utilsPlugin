package com.wagologies.utilsplugin.utils.gui.pagenation;

import com.wagologies.utilsplugin.UtilsPlugin;
import com.wagologies.utilsplugin.utils.gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Pagenation implements com.wagologies.utilsplugin.utils.gui.Preset {

    public Preset preset;

    public int pageNumber = 0;

    public Pagenation(Preset preset)
    {
        this.preset = preset;
    }

    @Override
    public String getName() {
        return preset.getName();
    }

    @Override
    public int getSize() {
        return preset.getSize();
    }

    public List<Integer> getInsideSlots()
    {
        List<Integer> slots = new ArrayList<>();
        int columns = 9;
        int rows = getSize() / columns;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int index = (row*columns) + column;
                if(row != 0 && row != rows-1) {
                    if(column != 0 && column != columns-1) {
                        slots.add(index);
                    }
                }
            }
        }
        return slots;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        Page page = preset.getPages().get(pageNumber);
        Iterator<Map.Entry<Function<Player, ItemStack>, Consumer<Player>>> it = page.getItems().entrySet().iterator();
        for (int i = 0; i < getInsideSlots().size(); i++) {
            int insideSlot = getInsideSlots().get(i);
            if(it.hasNext()) {
                Map.Entry<Function<Player, ItemStack>, Consumer<Player>> entry = it.next();
                items.add(new Item(insideSlot, entry.getKey(), entry.getValue()));
            }
            else {
                items.add(new Item(insideSlot, player -> {
                    ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
                    return stack;
                }, player -> {
                }));
            }
        }
        if(pageNumber != 0)
        {
            items.add(new Item(preset.getSize()-9, player -> {
                ItemStack backArrow = new ItemStack(Material.ARROW);
                ItemMeta backArrowMeta = backArrow.getItemMeta();
                backArrowMeta.setDisplayName("Previous Page");
                backArrow.setItemMeta(backArrowMeta);
                return backArrow;
            }, player -> pageNumber--));
        }
        if(pageNumber != preset.getPages().size() - 1)
        {
            items.add(new Item(preset.getSize()-1, player -> {
                ItemStack forwardArrow = new ItemStack(Material.ARROW);
                ItemMeta backArrowMeta = forwardArrow.getItemMeta();
                backArrowMeta.setDisplayName("Next Page");
                forwardArrow.setItemMeta(backArrowMeta);
                return forwardArrow;
            }, player -> pageNumber++));
        }
        return items;
    }
}
