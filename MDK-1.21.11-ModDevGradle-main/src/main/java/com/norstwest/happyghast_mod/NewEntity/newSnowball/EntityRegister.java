package com.norstwest.happyghast_mod.NewEntity.newSnowball;

import com.norstwest.happyghast_mod.HappyGhast_Extend;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, HappyGhast_Extend.MODID);

    public static final Supplier<EntityType<EntityClasss>> CUSTOM_SNOWBALL =
            ENTITIES.register("custom_snowball",
                    () -> EntityType.Builder.<EntityClasss>of(EntityClasss::new, MobCategory.MISC)
                            .sized(1.0F, 1.0F)
                            .clientTrackingRange(10)
                            .updateInterval(3)
                            .build(ResourceKey.create(
                                    Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(HappyGhast_Extend.MODID, "custom_snowball")
                            ))
            );
}