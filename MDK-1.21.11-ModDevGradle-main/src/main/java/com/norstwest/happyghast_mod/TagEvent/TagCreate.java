package com.norstwest.happyghast_mod.TagEvent;

import net.minecraft.world.entity.animal.happyghast.HappyGhast;

import java.util.Optional;

public class TagCreate {
    public static final String FED_BY_PLAYER_TAG = "FedByPlayer";

    public static Optional<Boolean> isFedByPlayer(HappyGhast ghast) {
        return ghast.getPersistentData().getBoolean(FED_BY_PLAYER_TAG);
    }

    public static void setFedByPlayer(HappyGhast ghast, boolean value) {
        ghast.getPersistentData().putBoolean(FED_BY_PLAYER_TAG, value);
    }
}
