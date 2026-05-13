package com.norstwest.happyghast_mod.NewEntity.newSnowball;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EntityClasss extends ThrowableProjectile implements ItemSupplier {

    private static final ItemStack SNOWBALL_ITEM = new ItemStack(Items.SNOWBALL);
    private static final ParticleOptions SNOWBALL_PARTICLE = ParticleTypes.ITEM_SNOWBALL;
    private static final int MAX_LIFESPAN = 200;
    private static final float DAMAGE_AMOUNT = 6.0F;

    private int explosionPower = 2;
    private LivingEntity owner;
    private boolean hasExploded = false;
    private DamageSource cachedDamageSource;


    public EntityClasss(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public EntityClasss(Level level, LivingEntity owner, double x, double y, double z) {
        super(EntityRegister.CUSTOM_SNOWBALL.get(), level);
        this.owner = owner;
        this.setOwner(owner);
        this.setPos(x, y, z);
        this.setNoGravity(true);
    }

    public void setExplosionPower(int power) {
        this.explosionPower = Math.max(1, Math.min(power, 5));
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
        super.tick();

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
            result.getEntity().hurt(this.getDamageSource(), DAMAGE_AMOUNT);
            this.explode();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide() && !hasExploded) {
            this.explode();
        }
    }

    private void explode() {
        if (hasExploded) return;
        hasExploded = true;

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.explode(this, this.getX(), this.getY(), this.getZ(),
                    this.explosionPower, false, ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }
}