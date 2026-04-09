package com.norstwest.happyghast_mod.EntityEvent;

import com.norstwest.happyghast_mod.HappyGhast_Extend;
import com.norstwest.happyghast_mod.NewGhast.SmallGhastEntity_Register;
import com.norstwest.happyghast_mod.NewGhast.SmallGhast_EntityClass;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = HappyGhast_Extend.MODID)
public class AttributeUpdate {


    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity living)) return;
        if (living.getType() != EntityType.HAPPY_GHAST ) return;

        living.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100.0D);
        living.setHealth(100.0F);
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Pre event) {
        if (!(event.getEntity() instanceof SmallGhast_EntityClass living)) return;
        if (living.getType() != SmallGhastEntity_Register.MEDIUM_GHAST.get()) return;

        AttributeInstance flySpeed = living.getAttribute(Attributes.FLYING_SPEED);
        if (flySpeed == null) return;

        boolean isRidden = living.isVehicle() && living.getControllingPassenger() != null;
        double targetSpeed = isRidden ? 0.10D : 0.03D;

        if (Math.abs(flySpeed.getBaseValue() - targetSpeed) > 0.001) {
            flySpeed.setBaseValue(targetSpeed);
        }
    }


}