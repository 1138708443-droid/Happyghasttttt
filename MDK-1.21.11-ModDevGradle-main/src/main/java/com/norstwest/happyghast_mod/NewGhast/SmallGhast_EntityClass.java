package com.norstwest.happyghast_mod.NewGhast;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SmallGhast_EntityClass extends HappyGhast {

    public SmallGhast_EntityClass(EntityType<? extends HappyGhast> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.@NonNull Builder createAttributes() {
        return HappyGhast.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.FLYING_SPEED, 0.03D);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        if (!this.isOnStillTimeout()) {
            Entity passenger = this.getFirstPassenger();
            if (passenger instanceof Player) {
                return (Player) passenger;
            }
        }
        return null;
    }

    @Override
    public boolean supportQuadLeashAsHolder() {
        return false;
    }

    @Override
    public float getAgeScale() {
        return 1.05F;
    }

    @Override
    protected @NonNull Vec2 getRiddenRotation(@NonNull LivingEntity entity) {
        if (entity instanceof Player player) {
            return new Vec2(player.getXRot() * 0.5F, player.getYRot());
        }
        return super.getRiddenRotation(entity);
    }

    @Override
    protected boolean canAddPassenger(@NonNull Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public boolean isBaby() {
        return false;
    }
}