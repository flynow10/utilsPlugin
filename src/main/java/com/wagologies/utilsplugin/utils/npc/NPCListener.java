package com.wagologies.utilsplugin.utils.npc;

import com.wagologies.utilsplugin.UtilsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NPCListener implements Listener {

    private NPC npc;

    public NPCListener(NPC npc)
    {
        this.npc = npc;
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event)
    {
        if(event.getPlayer().getLocation().getWorld().equals(npc.getLocation().getWorld()));
        {
            npc.checkLocation(event.getPlayer());
        }
    }
}
