package com.norstwest.happyghast_mod.NewGhast;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        // 简化逻辑：只要有乘客就返回第一个玩家
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
        return true;  // 原版返回 true
    }

    @Override
    public float getAgeScale() {
        return 1.05F;  // 自定义大小，介于幼年(0.2375F)和成年(1.0F)之间
    }

    @Override
    public boolean isBaby() {

        return false;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (!(this.level() instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }


        if (!player.isPassenger() && !this.isVehicle()) {
            player.startRiding(this);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public void addAdditionalSaveData(@NotNull ValueOutput output) {
        super.addAdditionalSaveData(output);

    }

    @Override
    public void readAdditionalSaveData(@NotNull ValueInput input) {
        super.readAdditionalSaveData(input);

    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, LevelReader level) {
        if (!level.isEmptyBlock(pos)) {
            return 0.0F;
        }
        return level.isEmptyBlock(pos.below()) ? 10.0F : 5.0F;
    }
}