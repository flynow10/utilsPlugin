package com.wagologies.utilsplugin.utils.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.mojang.authlib.GameProfile;
import com.wagologies.utilsplugin.UtilsPlugin;
import com.wagologies.utilsplugin.utils.npc.packets.PacketPlayOutEntityHeadRotationWrapper;
import com.wagologies.utilsplugin.utils.npc.packets.PacketPlayOutPlayerInfoWrapper;
import com.wagologies.utilsplugin.utils.npc.packets.PacketPlayOutPlayerNamedEntitySpawnWrapper;
import com.wagologies.utilsplugin.utils.npc.packets.PacketPlayOutScoreboardTeamWrapper;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class NPC {
    private static HashMap<Integer, NPC> npcs = new LinkedHashMap<>();
    private List<Player> seeingPlayers = new ArrayList<>();
    private int seeDistance;
    private Location location;

    private UUID uuid = new UUID(new Random().nextLong(), 0);
    private String name = uuid.toString().replace("-", "").substring(0, 10);
    private GameProfile gameProfile = new GameProfile(uuid, name);
    private int entityId = Integer.MAX_VALUE - npcs.size();
    private boolean spawned = false;
    private NPCListener listener = new NPCListener(this);
    private NPC that = this;
    private PacketAdapter adapter = new PacketAdapter(UtilsPlugin.getInstance(), ListenerPriority.NORMAL,
            PacketType.Play.Client.USE_ENTITY) {
        @Override
        public void onPacketReceiving(PacketEvent event) {
            if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                PacketContainer packetContainer = event.getPacket();
                EnumWrappers.EntityUseAction entityUseAction = packetContainer.getEntityUseActions().read(0);
                int entityId = packetContainer.getIntegers().read(0);
                if(entityId == that.entityId) {
                    if(!(entityUseAction == EnumWrappers.EntityUseAction.INTERACT_AT)) {
                        NPCInteractEvent interactEvent = new NPCInteractEvent(that, (entityUseAction == EnumWrappers.EntityUseAction.ATTACK) ? NPCInteractEvent.InteractType.LeftClick : NPCInteractEvent.InteractType.RightClick, event.getPlayer());
                        Bukkit.getPluginManager().callEvent(interactEvent);
                    }
                }
            }
        }
    };

    private PacketPlayOutNamedEntitySpawn packetPlayOutNamedEntitySpawn;
    private PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeamRegister;
    private PacketPlayOutPlayerInfo packetPlayOutPlayerInfoAdd, packetPlayOutPlayerInfoRemove;
    private PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation;
    private PacketPlayOutEntityDestroy packetPlayOutEntityDestroy;

    public NPC(Location location, int seeDistance)
    {
        npcs.put(npcs.size(), this);
        this.location = location;
        this.seeDistance = seeDistance;
        CreatePackets();
        spawn();
    }

    public void spawn()
    {
        seeingPlayers.addAll(Bukkit.getOnlinePlayers().stream().filter(entity -> entity.getWorld() == location.getWorld() && entity.getLocation().distance(location) <= seeDistance).collect(Collectors.toList()));
        SendShowPackets();
        Bukkit.getPluginManager().registerEvents(listener, UtilsPlugin.getInstance());
        ProtocolLibrary.getProtocolManager().addPacketListener(adapter);
        spawned = true;
    }

    public void remove()
    {
        HandlerList.unregisterAll(listener);
        SendHidePackets();
        seeingPlayers.clear();
        ProtocolLibrary.getProtocolManager().removePacketListener(adapter);
        spawned = false;
    }

    public static void Remove(int id)
    {
        npcs.get(id).remove();
        npcs.remove(id);
    }

    public static NPC GetNPC(int id)
    {
        return npcs.get(id);
    }

    private void CreatePackets()
    {
        this.packetPlayOutScoreboardTeamRegister = new PacketPlayOutScoreboardTeamWrapper()
                .createRegisterTeam(name);
        this.packetPlayOutPlayerInfoAdd = new PacketPlayOutPlayerInfoWrapper()
                .create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, gameProfile, name);
        this.packetPlayOutNamedEntitySpawn = new PacketPlayOutPlayerNamedEntitySpawnWrapper()
                .create(uuid, location, entityId);
        this.packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotationWrapper()
                .create(location, entityId);
        this.packetPlayOutPlayerInfoRemove = new PacketPlayOutPlayerInfoWrapper()
                .create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, gameProfile, name);
        this.packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityId);
    }

    private void SendShowPackets()
    {
        for (Player seeingPlayer : seeingPlayers) {
            SendShowPackets(seeingPlayer);
        }
    }

    private void SendShowPackets(Player player)
    {
        PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;
        playerConnection.sendPacket(packetPlayOutScoreboardTeamRegister);
        playerConnection.sendPacket(packetPlayOutPlayerInfoAdd);
        playerConnection.sendPacket(packetPlayOutNamedEntitySpawn);
        playerConnection.sendPacket(packetPlayOutEntityHeadRotation);

        Bukkit.getScheduler().runTaskLater(UtilsPlugin.getInstance(), () ->
                playerConnection.sendPacket(packetPlayOutPlayerInfoRemove), 200);
    }

    private void SendHidePackets()
    {
        for (Player seeingPlayer : seeingPlayers) {
            SendHidePackets(seeingPlayer);
        }
    }

    private void SendHidePackets(Player player)
    {
        PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;
        playerConnection.sendPacket(packetPlayOutEntityDestroy);
        playerConnection.sendPacket(packetPlayOutPlayerInfoRemove);
    }


    @SuppressWarnings("ConstantConditions")
    public int getId()
    {
        return npcs.containsValue(this) ? getKey(npcs,this) : 0 ;
    }

    public Location getLocation()
    {
        return location;
    }

    public void checkLocation(Player player)
    {
        if(seeingPlayers.contains(player))
        {
            if(player.getLocation().distance(getLocation()) > seeDistance)
            {
                SendHidePackets(player);
                seeingPlayers.remove(player);
            }
        }
        else
        {
            if(player.getLocation().distance(getLocation()) <= seeDistance)
            {
                SendShowPackets(player);
                seeingPlayers.add(player);
            }
        }
    }

    public void lookAt(Location location)
    {
        for (Player player : seeingPlayers) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

            Location npcLocation = getLocation();
            Vector dirBetweenLocations = location.toVector().subtract(npcLocation.toVector());

            npcLocation.setDirection(dirBetweenLocations);
            this.location.setDirection(dirBetweenLocations);
            this.packetPlayOutNamedEntitySpawn = new PacketPlayOutPlayerNamedEntitySpawnWrapper()
                    .create(uuid, this.location, entityId);
            this.packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotationWrapper()
                    .create(this.location, entityId);
            float yaw = npcLocation.getYaw();
            float pitch = npcLocation.getPitch();

            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(entityId, (byte) ((yaw % 360.) * 256 / 360), (byte) ((pitch % 360.) * 256 / 360), false));
            connection.sendPacket(new PacketPlayOutEntityHeadRotationWrapper().create(npcLocation, entityId));
        }
    }

    private <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
