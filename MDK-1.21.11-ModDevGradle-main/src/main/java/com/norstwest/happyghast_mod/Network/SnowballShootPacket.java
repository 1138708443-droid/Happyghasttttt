package com.norstwest.happyghast_mod.Network;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import com.norstwest.happyghast_mod.NewEntity.newSnowball.EntityClasss;

import static com.norstwest.happyghast_mod.HappyGhast_Extend.MODID;

public record SnowballShootPacket() implements CustomPacketPayload {

    private static final double MOUTH_OFFSET_Y_RATIO = 0.25;
    private static final double MOUTH_OFFSET_FORWARD = 2.2;
    private static final double PROJECTILE_SPEED = 0.8;
    private static final int EXPLOSION_POWER = 6;

    private static final double DEG_TO_RAD = Math.PI / 180.0;

    public static final Type<SnowballShootPacket> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(MODID, "shoot"));

    public static final StreamCodec<FriendlyByteBuf, SnowballShootPacket> CODEC =
            StreamCodec.unit(new SnowballShootPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SnowballShootPacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) ctx.player();
            if (!(player.getVehicle() instanceof HappyGhast ghast)) {
                return;
            }

            Vec3 look = player.getLookAngle();

            float yRot = ghast.getYRot();
            double rad = yRot * DEG_TO_RAD;
            double forwardX = -Math.sin(rad);
            double forwardZ = Math.cos(rad);

            double mouthOffsetY = ghast.getBbHeight() * MOUTH_OFFSET_Y_RATIO;

            EntityClasss projectile = new EntityClasss(
                    ghast.level(),
                    ghast,
                    ghast.getX() + forwardX * MOUTH_OFFSET_FORWARD,
                    ghast.getY() + mouthOffsetY,
                    ghast.getZ() + forwardZ * MOUTH_OFFSET_FORWARD
            );

            projectile.setDeltaMovement(
                    look.x * PROJECTILE_SPEED,
                    look.y * PROJECTILE_SPEED,
                    look.z * PROJECTILE_SPEED
            );
            projectile.setExplosionPower(EXPLOSION_POWER);

            ghast.level().addFreshEntity(projectile);
        });
    }
}