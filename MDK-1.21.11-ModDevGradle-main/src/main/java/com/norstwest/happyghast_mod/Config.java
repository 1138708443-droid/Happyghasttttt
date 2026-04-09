package com.norstwest.happyghast_mod;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue USE_NEOCONTROL = BUILDER
            .comment("Whether to use custom control for Happy Ghast",
                    "true: WASD horizontal movement, Space up, C down",
                    "false: Vanilla control (pitch affects flight direction)"
            )
            .define("useCustomControl", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}