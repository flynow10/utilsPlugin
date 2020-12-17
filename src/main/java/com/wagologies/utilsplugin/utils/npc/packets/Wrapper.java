package com.wagologies.utilsplugin.utils.npc.packets;

import java.lang.reflect.Field;

public class Wrapper {
    public void setField(Object packet, String field, Object value) {
        try {
            Field f = packet.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(packet, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
