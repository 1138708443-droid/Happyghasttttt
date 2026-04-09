package com.norstwest.happyghast_mod.NewGhast;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HappyGhastRenderer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jspecify.annotations.NonNull;

@OnlyIn(Dist.CLIENT)
public class SmallGhast_Renderer extends HappyGhastRenderer {


    private static final float MODEL_SCALE = 1.58F;

    public SmallGhast_Renderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F * MODEL_SCALE;
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull HappyGhastRenderState state) {
        return Identifier.fromNamespaceAndPath("minecraft", "textures/entity/ghast/happy_ghast_baby.png");
    }

    @Override
    public void extractRenderState(@NonNull HappyGhast entity, @NonNull HappyGhastRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isBaby = true;
    }


    @Override
    protected void scale(HappyGhastRenderState state, PoseStack poseStack) {
        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}