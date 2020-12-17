package com.wagologies.utilsplugin.utils.npc.packets;

import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import org.bukkit.Location;

public class PacketPlayOutEntityHeadRotationWrapper extends Wrapper {
    public PacketPlayOutEntityHeadRotation create(Location location, int entityId) {
        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation();

        setField(packetPlayOutEntityHeadRotation, "a", entityId);
        setField(packetPlayOutEntityHeadRotation, "b", (byte) ((int) location.getYaw() * 256.0F / 360.0F));

        return packetPlayOutEntityHeadRotation;
    }
}
