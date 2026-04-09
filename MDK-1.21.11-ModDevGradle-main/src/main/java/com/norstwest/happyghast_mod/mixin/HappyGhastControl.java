package com.norstwest.happyghast_mod.mixin;

import com.norstwest.happyghast_mod.Config;
import com.norstwest.happyghast_mod.KeyBindings;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HappyGhast.class)
public class HappyGhastControl {

    @Inject(
            method = "getRiddenInput",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onGetRiddenInput(Player player, Vec3 input, CallbackInfoReturnable<Vec3> cir) {

        if (!Config.USE_NEOCONTROL.get()) {
            return;
        }

        float xxa = player.xxa;
        float zza = player.zza;

        double vertical = 0.0;
        if (player.isJumping()) {
            vertical = 0.7;
        }
        if (KeyBindings.DOWN_KEY.isDown()) {
            vertical = -0.7;
        }

        Vec3 movement = new Vec3(xxa, vertical, zza);
        double speed = 3.9 * ((HappyGhast)(Object)this).getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED);
        cir.setReturnValue(movement.scale(speed));
    }
}