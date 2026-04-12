package com.norstwest.happyghast_mod.NewGhast;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.server.level.ServerLevel;
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
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder builder) {
        super.defineSynchedData(builder);
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
    public @NonNull InteractionResult mobInteract(@NonNull Player player, @NonNull InteractionHand hand) {
        if (!(this.level() instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }

        if (!player.isPassenger()) {
            player.startRiding(this);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected boolean canAddPassenger(net.minecraft.world.entity.@NonNull Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void addAdditionalSaveData(@NonNull ValueOutput output) {

        super.addAdditionalSaveData(output);
    }

    @Override
    public void readAdditionalSaveData(@NonNull ValueInput input) {

        super.readAdditionalSaveData(input);
    }

    @Override
    public boolean isBaby() {

        return false;
    }

}