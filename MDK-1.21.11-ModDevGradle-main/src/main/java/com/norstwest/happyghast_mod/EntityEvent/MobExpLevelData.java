package com.norstwest.happyghast_mod.EntityEvent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record MobExpLevelData (int level, int exp) {


    public static final Codec<MobExpLevelData> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.INT.fieldOf("level").forGetter(MobExpLevelData::level),
            Codec.INT.fieldOf("exp").forGetter(MobExpLevelData::exp)
    ).apply(inst, MobExpLevelData::new));

    public MobExpLevelData addExp(int amount) {
        int newExp = exp + amount;
        int newLevel = level;

        while (newExp >= 10) {
            newExp -= 10;
            newLevel += 1;
        }

        return new MobExpLevelData(newLevel, newExp);
    }


    public MobExpLevelData levelUp() {
        return new MobExpLevelData(level + 1, exp);
    }
}