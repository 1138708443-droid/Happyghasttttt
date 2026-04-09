package com.norstwest.happyghast_mod.NewGhast;

import com.norstwest.happyghast_mod.HappyGhast_Extend;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = HappyGhast_Extend.MODID)
public class SmallGhast_Attributes {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(
                SmallGhastEntity_Register.MEDIUM_GHAST.get(),
                SmallGhast_EntityClass.createAttributes().build()
        );
    }
}