package com.norstwest.happyghast_mod.NewGhast;

import com.norstwest.happyghast_mod.HappyGhast_Extend;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = HappyGhast_Extend.MODID)
public class Renderer_Register {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(SmallGhastEntity_Register.MEDIUM_GHAST.get(), SmallGhast_Renderer::new);
    }

}
