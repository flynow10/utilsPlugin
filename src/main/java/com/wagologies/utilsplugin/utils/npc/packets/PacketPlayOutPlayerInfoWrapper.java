package com.wagologies.utilsplugin.utils.npc.packets;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.WorldSettings;

import java.util.Collections;
import java.util.List;

public class PacketPlayOutPlayerInfoWrapper extends Wrapper {
    public PacketPlayOutPlayerInfo create(PacketPlayOutPlayerInfo.EnumPlayerInfoAction action, GameProfile gameProfile, String name) {
        PacketPlayOutPlayerInfo packetPlayOutPlayerInfo = new PacketPlayOutPlayerInfo();
        setField(packetPlayOutPlayerInfo, "a", action);

        PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = packetPlayOutPlayerInfo.new PlayerInfoData(gameProfile, 1,
                WorldSettings.EnumGamemode.NOT_SET, IChatBaseComponent.ChatSerializer.a("{\"text\":\"[NPC] " + name + "\",\"color\":\"dark_gray\"}"));

        setField(packetPlayOutPlayerInfo, "b", Collections.singletonList(playerInfoData));

        return packetPlayOutPlayerInfo;
    }
}
