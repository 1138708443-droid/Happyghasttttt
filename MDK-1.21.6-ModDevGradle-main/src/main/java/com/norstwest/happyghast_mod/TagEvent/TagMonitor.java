package com.norstwest.happyghast_mod.TagEvent;

import com.norstwest.happyghast_mod.HappyGhast_Extend;
import com.norstwest.happyghast_mod.NewGhast.SmallGhastEntity_Register;
import com.norstwest.happyghast_mod.NewGhast.SmallGhast_EntityClass;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.HappyGhast;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = HappyGhast_Extend.MODID)
public class TagMonitor {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {

        if (event.getHand() != InteractionHand.MAIN_HAND) return;
        if (!(event.getTarget() instanceof HappyGhast ghast)) return;
        if (!ghast.isBaby()) return;

        ItemStack itemInHand = event.getItemStack();
        if (itemInHand.getItem() != Items.GOLDEN_CARROT) return;


        boolean alreadyFed = TagCreate.isFedByPlayer(ghast).orElse(false);

        if (alreadyFed) {

            if (event.getLevel() instanceof ServerLevel serverLevel) {

                for (int i = 0; i < 20; i++) {
                    double offsetX = (serverLevel.random.nextDouble() - 0.5D) * 1.5D;
                    double offsetY = serverLevel.random.nextDouble() * 1.5D;
                    double offsetZ = (serverLevel.random.nextDouble() - 0.5D) * 1.5D;

                    serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                            ghast.getX() + offsetX,
                            ghast.getY() + offsetY,
                            ghast.getZ() + offsetZ,
                            1,
                            0.0D, 0.0D, 0.0D,
                            1.0D);
                }
            }
            event.setCanceled(true);
            return;
        }

        if (!event.getLevel().isClientSide()) {

            if (!event.getEntity().isCreative()) {
                itemInHand.shrink(1);
            }

            TagCreate.setFedByPlayer(ghast, true);

            if (event.getLevel() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.HEART,
                        ghast.getX(), ghast.getY() + 0.5D, ghast.getZ(),
                        20,
                        0.5D,
                        0.5D,
                        0.5D,
                        1.0D);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof HappyGhast ghast)) return;
        if (!ghast.isBaby()) return;

        boolean hasTag = TagCreate.isFedByPlayer(ghast).orElse(false);
        if (!hasTag) return;

        int age = ghast.getAge();
        if (age > -12000) {
            if (ghast.level() instanceof ServerLevel serverLevel) {

                SmallGhast_EntityClass newGhast = new SmallGhast_EntityClass(
                        SmallGhastEntity_Register.MEDIUM_GHAST.get(),
                        serverLevel
                );

                newGhast.setPos(ghast.getX(), ghast.getY(), ghast.getZ());
                newGhast.setYRot(ghast.getYRot());
                newGhast.setXRot(ghast.getXRot());

                if (ghast.hasCustomName()) {
                    newGhast.setCustomName(ghast.getCustomName());
                    newGhast.setCustomNameVisible(ghast.isCustomNameVisible());
                }

                if (ghast.isLeashed()) {
                    newGhast.setLeashedTo(ghast.getLeashHolder(), true);
                }

                serverLevel.addFreshEntity(newGhast);

                ghast.discard();

                serverLevel.sendParticles(ParticleTypes.POOF,
                        ghast.getX(), ghast.getY() + 1.0D, ghast.getZ(),
                        20, 0.5D, 0.5D, 0.5D, 0.0D);
            }
        }
    }
}