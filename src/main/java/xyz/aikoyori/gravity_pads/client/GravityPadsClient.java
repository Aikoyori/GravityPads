package xyz.aikoyori.gravity_pads.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import xyz.aikoyori.gravity_pads.registry.GPBlocks;

public class GravityPadsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockRenderLayerMap.INSTANCE.putBlock(GPBlocks.DIRECTIONAL_GRAVITY_BLOCK, RenderLayer.getTranslucent());
		// i give up for now
		//PadPreviewOverlay.initialize();
	}
}
