package com.norstwest.happyghast_mod.NewGhast;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HappyGhastRenderer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.HappyGhast;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SmallGhast_Renderer extends HappyGhastRenderer {

    private static final ResourceLocation GHAST_BABY_LOCATION =
            ResourceLocation.withDefaultNamespace("textures/entity/ghast/happy_ghast_baby.png");
    private static final float MODEL_SCALE = 1.58F;

    public SmallGhast_Renderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F * MODEL_SCALE;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull HappyGhastRenderState state) {
        return GHAST_BABY_LOCATION;
    }

    @Override
    public void extractRenderState(@NotNull HappyGhast entity, @NotNull HappyGhastRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isBaby = true;
    }

    @Override
    protected void scale(@NotNull HappyGhastRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}