package com.norstwest.happyghast_mod.EntityEvent;

import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;


import static com.norstwest.happyghast_mod.HappyGhast_Extend.MODID;

@EventBusSubscriber(modid = MODID)
public class HG_EXPLevelData {

    //Supplier<AttachmentType<Integer>> EXP = MOB_EXP;

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<MobExpLevelData>> MOB_DATA =
            ATTACHMENTS.register("mob_data", () ->
                    AttachmentType.builder(() -> new MobExpLevelData(0, 0))
                            .serialize(MobExpLevelData.CODEC.fieldOf("mob_data"))
                            .sync(ByteBufCodecs.fromCodec(MobExpLevelData.CODEC))
                            .build()
            );


    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getLevel().isClientSide() && event.getTarget() instanceof LivingEntity living) {
            Player player = event.getEntity();
            ItemStack stack = player.getMainHandItem();

            if (stack.is(Items.STICK)) {
                MobExpLevelData data = living.getData(HG_EXPLevelData.MOB_DATA);
                player.displayClientMessage(
                        Component.literal("§a等级: " + data.level() + "  经验: " + data.exp()),
                        true
                );
            }
        }
    }


}