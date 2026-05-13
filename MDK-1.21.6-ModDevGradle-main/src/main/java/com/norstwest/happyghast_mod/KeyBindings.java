package com.norstwest.happyghast_mod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = HappyGhast_Extend.MODID, value = Dist.CLIENT)
public class KeyBindings {

    public static final KeyMapping DOWN_KEY = new KeyMapping(
            "key.happyghast.down",
            InputConstants.KEY_C,
            "key.categories.movement"
    );

    public static final KeyMapping ATTACK_KEY = new KeyMapping(
            "key.happyghast.attack",
            InputConstants.KEY_Z,
            "key.categories.movement"
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ATTACK_KEY);
        event.register(DOWN_KEY);
    }
}