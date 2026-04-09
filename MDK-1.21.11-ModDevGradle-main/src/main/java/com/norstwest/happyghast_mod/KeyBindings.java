package com.norstwest.happyghast_mod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(Dist.CLIENT)
public class KeyBindings {

    public static final KeyMapping DOWN_KEY = new KeyMapping(
            "key.happyghast.down",
            InputConstants.KEY_C,
            KeyMapping.Category.MOVEMENT
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(DOWN_KEY);
    }
}