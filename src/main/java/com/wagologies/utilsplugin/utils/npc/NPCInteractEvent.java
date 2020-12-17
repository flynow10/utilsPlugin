package com.wagologies.utilsplugin.utils.npc;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCInteractEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private NPC npc;
    private InteractType type;
    private Player player;

    public NPCInteractEvent(NPC npc, InteractType type, Player player)
    {
        this.npc = npc;
        this.type = type;
        this.player = player;
    }

    public NPC getNpc() {
        return npc;
    }

    public InteractType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    enum InteractType {
        LeftClick,
        RightClick
    }
}
