package com.norstwest.happyghast_mod.mixin;

import net.minecraft.client.model.HappyGhastHarnessModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhastHarnessModel.class)
public class HappyGhastHarnessModelMixin {

    @Shadow
    @Final
    private ModelPart goggles;

    @ModifyArg(
            method = "createHarnessLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/geom/builders/LayerDefinition;apply(Lnet/minecraft/client/model/geom/builders/MeshTransformer;)Lnet/minecraft/client/model/geom/builders/LayerDefinition;",
                    ordinal = 1
            ),
            index = 0
    )
    private static MeshTransformer disableBabyTransformer(MeshTransformer transformer) {
        return MeshTransformer.IDENTITY;
    }

    @Inject(
            method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HappyGhastRenderState;)V",
            at = @At("TAIL")
    )
    private void adjustGogglesForBaby(HappyGhastRenderState state, CallbackInfo ci) {
        if (state.isBaby && state.isRidden) {
            this.goggles.y = 11.0F;
        }
    }
}