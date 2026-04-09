package com.norstwest.happyghast_mod;

import com.norstwest.happyghast_mod.EntityEvent.HG_EXPLevelData;
import com.norstwest.happyghast_mod.NewGhast.SmallGhastEntity_Register;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.*;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


@Mod(HappyGhast_Extend.MODID)
public class HappyGhast_Extend {

    public static final String MODID = "happyghast_extend";

    public static final Logger LOGGER = LogUtils.getLogger();



    public HappyGhast_Extend(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);

        HG_EXPLevelData.ATTACHMENTS.register(modEventBus);

        SmallGhastEntity_Register.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        LOGGER.info("HELLO FROM COMMON SETUP");

    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
