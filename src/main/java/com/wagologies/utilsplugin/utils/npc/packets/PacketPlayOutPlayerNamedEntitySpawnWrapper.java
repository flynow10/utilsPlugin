package com.wagologies.utilsplugin.utils.npc.packets;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import org.bukkit.Location;

import java.util.UUID;

public class PacketPlayOutPlayerNamedEntitySpawnWrapper extends Wrapper {
    public PacketPlayOutNamedEntitySpawn create(UUID uuid, Location location, int entityId) {
        PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn = new PacketPlayOutNamedEntitySpawn();

        setField(packetPlayOutNamedEntitySpawn, "a", entityId);
        setField(packetPlayOutNamedEntitySpawn, "b", uuid);
        setField(packetPlayOutNamedEntitySpawn, "c", (int) Math.floor(location.getX() * 32.0D));
        setField(packetPlayOutNamedEntitySpawn, "d", (int) Math.floor(location.getY() * 32.0D));
        setField(packetPlayOutNamedEntitySpawn, "e", (int) Math.floor(location.getZ() * 32.0D));
        setField(packetPlayOutNamedEntitySpawn, "f", (byte) ((int) (location.getYaw() * 256.0F / 360.0F)));
        setField(packetPlayOutNamedEntitySpawn, "g", (byte) ((int) (location.getPitch() * 256.0F / 360.0F)));
        DataWatcher dataWatcher = new DataWatcher(null);
        dataWatcher.a(10, (byte) 127);

        setField(packetPlayOutNamedEntitySpawn, "i", dataWatcher);

        return packetPlayOutNamedEntitySpawn;
    }
}
