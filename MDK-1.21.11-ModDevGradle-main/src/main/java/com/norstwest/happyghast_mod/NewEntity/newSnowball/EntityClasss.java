package com.norstwest.happyghast_mod.NewEntity.newSnowball;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class EntityClasss extends ThrowableProjectile implements ItemSupplier {

    private static final ItemStack SNOWBALL_ITEM = new ItemStack(Items.SNOWBALL);
    private static final ParticleOptions SNOWBALL_PARTICLE = ParticleTypes.ITEM_SNOWBALL;
    private static final int MAX_LIFESPAN = 200;
    private static final float DAMAGE_AMOUNT = 0.0F;

    private int explosionPower = 2;
    private LivingEntity owner;
    private boolean hasExploded = false;
    private DamageSource cachedDamageSource;

    public EntityClasss(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
    }

    public EntityClasss(Level level, LivingEntity owner, double x, double y, double z) {
        super(EntityRegister.CUSTOM_SNOWBALL.get(), level);
        this.owner = owner;
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.setNoGravity(true);
    }

    public void setExplosionPower(int power) {

        this.explosionPower = power;
    }

    @Override
    public ItemStack getItem() {
        return SNOWBALL_ITEM;
    }

    private DamageSource getDamageSource() {
        if (cachedDamageSource == null) {
            cachedDamageSource = this.damageSources().thrown(this, this.owner);
        }
        return cachedDamageSource;
    }

    @Override
    public void tick() {

        Vec3 currentMotion = this.getDeltaMovement();

        super.tick();

        if (!this.level().isClientSide() && currentMotion.lengthSqr() > 0) {
            this.setDeltaMovement(currentMotion);
        }

        if (this.level().isClientSide() && this.tickCount % 2 == 0) {
            this.level().addParticle(SNOWBALL_PARTICLE,
                    this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }

        if (this.tickCount > MAX_LIFESPAN) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide() && !hasExploded) {
            Entity target = result.getEntity();
            if (target != this.owner && target != this) {
                target.hurt(this.getDamageSource(), DAMAGE_AMOUNT);
            }
            this.explode();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide() && !hasExploded) {
            this.setPos(result.getLocation());
            this.explode();
        }
    }

    private void extinguishFireInRadius(double radius) {
        Level level = this.level();
        if (level.isClientSide()) return;

        BlockPos center = this.blockPosition();
        int r = (int)Math.ceil(radius);

        BlockPos.betweenClosedStream(
                center.offset(-r, -r, -r),
                center.offset(r, r, r)
        ).forEach(pos -> {
            if (level.getBlockState(pos).is(Blocks.FIRE)) {
                level.removeBlock(pos, false);
            }
        });


        AABB area = this.getBoundingBox().inflate(radius);
        level.getEntitiesOfClass(LivingEntity.class, area).forEach(entity -> {

            if (owner == null || entity != owner) {
                entity.clearFire();
            }
        });
    }

    private void explode() {
        if (hasExploded) return;
        hasExploded = true;

        if (this.level() instanceof ServerLevel serverLevel) {
            extinguishFireInRadius(this.explosionPower);

            serverLevel.explode(this, this.getX(), this.getY(), this.getZ(),
                    this.explosionPower, false, Level.ExplosionInteraction.NONE);

            BlockState waterBlock = Blocks.WATER.defaultBlockState();
            int blockCount = 200 + this.explosionPower * 5;
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, waterBlock),
                    this.getX(), this.getY(), this.getZ(),
                    blockCount,
                    this.explosionPower * 0.4,
                    this.explosionPower * 0.4,
                    this.explosionPower * 0.4,
                    0.6);



            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

}