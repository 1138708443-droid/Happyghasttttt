package com.norstwest.happyghast_mod.NewGhast;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmallGhast_EntityClass extends HappyGhast {

    public SmallGhast_EntityClass(EntityType<? extends HappyGhast> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
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
    protected @NotNull Vec2 getRiddenRotation(@NotNull LivingEntity entity) {
        if (entity instanceof Player player) {
            return new Vec2(player.getXRot() * 0.5F, player.getYRot());
        }
        return super.getRiddenRotation(entity);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public boolean isBaby() {
        return false;
    }
}