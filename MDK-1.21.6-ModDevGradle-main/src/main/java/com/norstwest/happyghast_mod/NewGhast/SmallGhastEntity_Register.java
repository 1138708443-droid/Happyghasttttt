package com.norstwest.happyghast_mod.NewGhast;

import com.norstwest.happyghast_mod.HappyGhast_Extend;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;  // 修改：Identifier → ResourceLocation
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SmallGhastEntity_Register {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, HappyGhast_Extend.MODID);

    public static final Supplier<EntityType<SmallGhast_EntityClass>> MEDIUM_GHAST =
            ENTITIES.register("medium_adult_ghast", () -> EntityType.Builder
                    .of(SmallGhast_EntityClass::new, MobCategory.CREATURE)
                    .sized(1.5F, 1.5F)
                    .clientTrackingRange(10)
                    .updateInterval(3)
                    .build(ResourceKey.create(
                            Registries.ENTITY_TYPE,
                            ResourceLocation.fromNamespaceAndPath(HappyGhast_Extend.MODID, "medium_adult_ghast")))  // 修改：Identifier → ResourceLocation
            );

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}