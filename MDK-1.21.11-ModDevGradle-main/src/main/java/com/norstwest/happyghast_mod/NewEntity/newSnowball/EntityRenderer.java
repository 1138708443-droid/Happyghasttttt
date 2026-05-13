package com.norstwest.happyghast_mod.NewEntity.newSnowball;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.norstwest.happyghast_mod.HappyGhast_Extend.MODID;

@EventBusSubscriber(value = Dist.CLIENT, modid = MODID)
public class EntityRenderer {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                EntityRegister.CUSTOM_SNOWBALL.get(),
                (context) -> new ThrownItemRenderer<>(context, 3.0F, true)
        );
    }
}