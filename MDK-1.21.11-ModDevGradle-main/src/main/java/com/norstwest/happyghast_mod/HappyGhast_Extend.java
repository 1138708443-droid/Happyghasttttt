package com.norstwest.happyghast_mod;

//import com.norstwest.happyghast_mod.EntityEvent.HG_EXPLevelData;
import com.norstwest.happyghast_mod.Network.SnowballShootPacket;
import com.norstwest.happyghast_mod.NewEntity.SmallGhastEntity_Register;

import com.norstwest.happyghast_mod.NewEntity.newSnowball.EntityClasss;
import com.norstwest.happyghast_mod.NewEntity.newSnowball.EntityRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;



@Mod(HappyGhast_Extend.MODID)
public class HappyGhast_Extend {

    public static final String MODID = "happyghast_extend";


    public HappyGhast_Extend(IEventBus modEventBus, ModContainer modContainer) {

        //HG_EXPLevelData.ATTACHMENTS.register(modEventBus);
        SmallGhastEntity_Register.register(modEventBus);

        EntityRegister.ENTITIES.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        modEventBus.addListener(this::registerPayloads);
    }

    public void registerPayloads(RegisterPayloadHandlersEvent event) {
        var reg = event.registrar(MODID)
                .versioned("1")
                .optional();

        reg.playToServer(
                SnowballShootPacket.TYPE,
                SnowballShootPacket.CODEC,
                SnowballShootPacket::handle
        );
    }
}
