package xyz.aikoyori.gravity_pads.client;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.framebuffer.Framebuffer;
import com.mojang.blaze3d.framebuffer.SimpleFramebuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.wispforest.owo.ui.event.WindowResizeCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.BlockPos.Mutable;
import org.joml.Quaternionf;
import org.lwjgl.opengl.GL30C;
import xyz.aikoyori.gravity_pads.GravityPads;
import xyz.aikoyori.gravity_pads.blocks.pads.DirectionalGravityPad;
import xyz.aikoyori.gravity_pads.config.GravityPadsConfig;
import xyz.aikoyori.gravity_pads.registry.GPPads;
import xyz.aikoyori.gravity_pads.utils.Constants;

public class PadPreviewOverlay {

	private static final Supplier<Framebuffer> FRAMEBUFFER = Suppliers.memoize(() -> {
		var window = MinecraftClient.getInstance().getWindow();

		var framebuffer = new SimpleFramebuffer(window.getFramebufferWidth(), window.getFramebufferHeight(), true, MinecraftClient.IS_SYSTEM_MAC);
		framebuffer.setClearColor(0f, 0f, 0f, 0f);
		return framebuffer;
	});

	private static void renderOverlayBlock(MatrixStack matrices, VertexConsumerProvider consumers, BlockPos offsetInStructure, BlockState state) {
		matrices.push();
		matrices.translate(offsetInStructure.getX(), offsetInStructure.getY(), offsetInStructure.getZ());

		matrices.translate(.5, .5, .5);
		matrices.scale(1.0001f, 1.0001f, 1.0001f);
		matrices.translate(-.5, -.5, -.5);

		MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(
				state,
				matrices,
				consumers,
				LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE,
				OverlayTexture.DEFAULT_UV
		);
		matrices.pop();
	}

	public static void initialize() {
		WorldRenderEvents.LAST.register(context -> {
			if(GravityPads.gravityPadConfig.enablePlacementHelper())
			{
				var matrices = context.matrixStack();
				matrices.push();


				matrices.translate(-context.camera().getPos().x, -context.camera().getPos().y, -context.camera().getPos().z);
				var client = MinecraftClient.getInstance();
				var effectConsumers = client.getBufferBuilders().getEffectVertexConsumers();
				var testPos = new BlockPos.Mutable();
				MinecraftClient.getInstance().getBlockRenderManager().renderBlock(Blocks.COMMAND_BLOCK.getDefaultState(),context.camera().getBlockPos(),context.world(),matrices,context.consumers().getBuffer(RenderLayer.getTranslucent()),true,context.world().getRandom());

				if(MinecraftClient.getInstance().player.isHolding(GPPads.DIRECTIONAL_GRAVITY_PAD.asItem()))
				{
					HitResult hit = MinecraftClient.getInstance().crosshairTarget;
					if(hit.getType() == HitResult.Type.BLOCK)
					{
						//GravityPads.LOGGER.info("IT IS GETTING CALLED :3");

						BlockHitResult bhr = (BlockHitResult)hit;
						//matrices.translate(-0.5f,-.5f,-.5f);
						int placementSide = Constants.getPlacementRegion(bhr.getPos(),bhr.getSide());
						Direction gravityDirection = Constants.getGravitySide(bhr.getSide(),placementSide);
						BlockState state = GPPads.DIRECTIONAL_GRAVITY_PAD.getDefaultState().with(DirectionalGravityPad.DIRECTION,bhr.getSide()).with(DirectionalGravityPad.GRAVITY_DIRECTION,gravityDirection);
						BlockPos bp = bhr.getBlockPos().add(bhr.getSide().getVector()).multiply(-1);
						matrices.translate(bp.getX(), bp.getY(), bp.getZ());
						RenderSystem.enableBlend();
						RenderSystem.defaultBlendFunc();
						renderOverlayBlock(matrices,context.consumers(),bp,state);
					}
				}

				matrices.pop();

				var framebuffer = FRAMEBUFFER.get();
				framebuffer.clear(MinecraftClient.IS_SYSTEM_MAC);
				framebuffer.beginWrite(false);

				GL30C.glBindFramebuffer(GL30C.GL_READ_FRAMEBUFFER, client.getFramebuffer().framebufferId);
				GL30C.glBlitFramebuffer(0, 0, framebuffer.textureWidth, framebuffer.textureHeight, 0, 0, client.getFramebuffer().textureWidth, client.getFramebuffer().textureHeight, GL30C.GL_DEPTH_BUFFER_BIT, GL30C.GL_NEAREST);

				if (context.consumers() instanceof VertexConsumerProvider.Immediate immediate) immediate.draw();
				effectConsumers.draw();
				client.getFramebuffer().beginWrite(false);

				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();

				client.gameRenderer.blitScreenShader.colorModulator.setFloats(new float[]{1, 1, 1, 0.5f});
				framebuffer.draw(framebuffer.textureWidth, framebuffer.textureHeight, false);
				client.gameRenderer.blitScreenShader.colorModulator.setFloats(new float[]{1, 1, 1, 1});

			}
		});

		WindowResizeCallback.EVENT.register((client, window) -> {
			FRAMEBUFFER.get().resize(window.getFramebufferWidth(), window.getFramebufferHeight(), MinecraftClient.IS_SYSTEM_MAC);
		});

	}
}
