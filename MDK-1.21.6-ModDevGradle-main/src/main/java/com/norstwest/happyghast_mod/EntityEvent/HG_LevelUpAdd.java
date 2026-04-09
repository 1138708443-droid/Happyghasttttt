package com.norstwest.happyghast_mod.EntityEvent;

import net.minecraft.world.entity.animal.HappyGhast;

import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import static com.norstwest.happyghast_mod.HappyGhast_Extend.MODID;


@EventBusSubscriber(modid = MODID)
public class HG_LevelUpAdd {

    @SubscribeEvent
    public static void onRightClickHappyghast(PlayerInteractEvent.EntityInteract event) {
        var player = event.getEntity();
        var target = event.getTarget();


        if (!(target instanceof HappyGhast happyghast)) return;

        var handItem = event.getItemStack();


        if (!handItem.is(Items.PAPER)) return;


        if (!player.isCreative()) {
            handItem.shrink(0);
        }

        MobExpLevelData data = happyghast.getData(HG_EXPLevelData.MOB_DATA);

        data = data.addExp(2);

        happyghast.setData(HG_EXPLevelData.MOB_DATA, data);

        event.setCanceled(true);
    }
}