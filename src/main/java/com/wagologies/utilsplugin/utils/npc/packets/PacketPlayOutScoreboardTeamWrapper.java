/*
 * Copyright (c) 2018 Jitse Boonstra
 */

package com.wagologies.utilsplugin.utils.npc.packets;

import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Jitse Boonstra
 */
public class PacketPlayOutScoreboardTeamWrapper extends Wrapper {

    public PacketPlayOutScoreboardTeam createRegisterTeam(String name) {
        PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam();

        setField(packetPlayOutScoreboardTeam, "h", 0);
        setField(packetPlayOutScoreboardTeam, "b", name);
        setField(packetPlayOutScoreboardTeam, "a", name);
        setField(packetPlayOutScoreboardTeam, "e", "never");
        setField(packetPlayOutScoreboardTeam, "i", 1);
        setField(packetPlayOutScoreboardTeam, "g", Collections.singletonList(name));
        return packetPlayOutScoreboardTeam;
    }

    public PacketPlayOutScoreboardTeam createUnregisterTeam(String name) {
        PacketPlayOutScoreboardTeam packetPlayOutScoreboardTeam = new PacketPlayOutScoreboardTeam();

        setField(packetPlayOutScoreboardTeam, "h", 1);
        setField(packetPlayOutScoreboardTeam, "a", name);

        return packetPlayOutScoreboardTeam;
    }
}