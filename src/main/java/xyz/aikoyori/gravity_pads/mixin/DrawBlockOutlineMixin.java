package xyz.aikoyori.gravity_pads.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.aikoyori.gravity_pads.blocks.pads.DirectionalGravityPad;
import xyz.aikoyori.gravity_pads.registry.GPPads;
import xyz.aikoyori.gravity_pads.utils.Constants;

@Mixin(WorldRenderer.class)
public class DrawBlockOutlineMixin {
	@Shadow
	private @Nullable ClientWorld world;

	@Inject(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/client/render/WorldRenderer;drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"),cancellable = true,locals = LocalCapture.CAPTURE_FAILSOFT)
	void gp$drawPreview(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f projectionMatrix, CallbackInfo ci){
		matrices.push();
		if(MinecraftClient.getInstance().player.isHolding(GPPads.DIRECTIONAL_GRAVITY_PAD.asItem()))
		{
			HitResult hit = MinecraftClient.getInstance().crosshairTarget;
			if(hit.getType() == HitResult.Type.BLOCK)
			{

				BlockHitResult bhr = (BlockHitResult)hit;
				//matrices.translate(-0.5f,-.5f,-.5f);
				int placementSide = Constants.getPlacementRegion(bhr.getPos(),bhr.getSide());
				Direction gravityDirection = Constants.getGravitySide(bhr.getSide(),placementSide);
				BlockState state = GPPads.DIRECTIONAL_GRAVITY_PAD.getDefaultState().with(DirectionalGravityPad.DIRECTION,bhr.getSide()).with(DirectionalGravityPad.GRAVITY_DIRECTION,gravityDirection);
				BlockRenderManager brm = MinecraftClient.getInstance().getBlockRenderManager();
				VertexConsumer vx = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getTranslucent());
				BlockPos bp = bhr.getBlockPos().add(bhr.getSide().getVector());
				Vec3d distanced = MinecraftClient.getInstance().player.getClientCameraPosVec(tickDelta).subtract(Vec3d.of(bp)).multiply(-1);
				matrices.translate((float)distanced.getX(),(float)distanced.getY(),(float)distanced.getZ());
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShaderColor(1,1,1,0.25f);
				MinecraftClient.getInstance().gameRenderer.blitScreenShader.colorModulator.setFloats(new float[]{1, 1, 1, .5f});
				brm.getModelRenderer().render(world,brm.getModel(state),state,bp,matrices,vx,false,world.getRandom(),state.getRenderingSeed(bp),OverlayTexture.DEFAULT_UV);
				MinecraftClient.getInstance().gameRenderer.blitScreenShader.colorModulator.setFloats(new float[]{1, 1, 1, 1});
			}
		}
		matrices.pop();
	}
}
