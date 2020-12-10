package com.wagologies.utilsplugin.utils.gui.pagenation;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Page {

    public Map<Function<Player, ItemStack>, Consumer<Player>> items;

    public Page(Map<Function<Player, ItemStack>, Consumer<Player>> items)
    {
        this.items = items;
    }

    public Map<Function<Player, ItemStack>, Consumer<Player>> getItems() {
        return items;
    }
}
