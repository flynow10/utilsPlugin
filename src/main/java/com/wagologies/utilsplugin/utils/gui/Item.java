package com.wagologies.utilsplugin.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class Item {
    public Function<Player, ItemStack> placeHolder;
    public Consumer<Player> callback;
    public int slot;
    public Item(int slot, Function<Player, ItemStack> placeHolder, Consumer<Player> callback)
    {
        this.slot = slot;
        this.placeHolder = placeHolder;
        this.callback = callback;
    }
}
