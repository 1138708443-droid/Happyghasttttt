package com.norstwest.happyghast_mod.EntityEvent;

import com.norstwest.happyghast_mod.KeyBindings;
import com.norstwest.happyghast_mod.Network.SnowballShootPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public final class AttackSkill {
    private static int cooldown = 0;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft ghastshot = Minecraft.getInstance();

        if (ghastshot.player == null || ghastshot.level == null || ghastshot.getConnection() == null) {
            return;
        }

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (!KeyBindings.ATTACK_KEY.isDown()) {
            return;
        }

        if (ghastshot.player.getVehicle() instanceof HappyGhast) {
            ghastshot.getConnection().send(new SnowballShootPacket());
            cooldown = 20;
        }
    }
}