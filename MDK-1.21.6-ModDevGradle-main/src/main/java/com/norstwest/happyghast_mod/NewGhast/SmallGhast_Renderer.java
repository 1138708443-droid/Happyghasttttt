package com.norstwest.happyghast_mod.NewGhast;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HappyGhastModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.HappyGhast;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class SmallGhast_Renderer extends AgeableMobRenderer<HappyGhast, HappyGhastRenderState, HappyGhastModel> {

    private static final ResourceLocation GHAST_BABY_LOCATION =
            ResourceLocation.withDefaultNamespace("textures/entity/ghast/happy_ghast_baby.png");
    private static final float MODEL_SCALE = 1.58F;

    public SmallGhast_Renderer(EntityRendererProvider.Context context) {

        super(context,
                new HappyGhastModel(context.bakeLayer(ModelLayers.HAPPY_GHAST)),
                new HappyGhastModel(context.bakeLayer(ModelLayers.HAPPY_GHAST_BABY)),
                2.0F
        );
        this.shadowRadius = 0.5F * MODEL_SCALE;
    }

    @Override
    public ResourceLocation getTextureLocation(HappyGhastRenderState state) {

        return GHAST_BABY_LOCATION;
    }

    @Override
    public HappyGhastRenderState createRenderState() {
        return new HappyGhastRenderState();
    }

    @Override
    public void extractRenderState(HappyGhast entity, HappyGhastRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.isBaby = true;
    }

    @Override
    protected void scale(HappyGhastRenderState state, PoseStack poseStack) {

        poseStack.scale(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE);
    }
}