package com.norstwest.happyghast_mod.Network;/*package com.norstwest.happyghast_mod.Network;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Objects;

public class PacketHandler {
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar reg = event.registrar("T");
        reg.playToServer(
                SnowballShootPacket.TYPE,
                SnowballShootPacket.CODEC,
                SnowballShootPacket::handle
        );
    }

    public static void sendSnowballPacket() {
        Objects.requireNonNull(Minecraft.getInstance().getConnection()).send(new SnowballShootPacket());
    }
}*/