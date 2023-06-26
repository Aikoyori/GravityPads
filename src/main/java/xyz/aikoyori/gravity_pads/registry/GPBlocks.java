package xyz.aikoyori.gravity_pads.registry;

import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import xyz.aikoyori.gravity_pads.blocks.abstracted.AbstractGravityPadBlock;
import xyz.aikoyori.gravity_pads.blocks.blocks.DirectionalGravityBlock;
import xyz.aikoyori.gravity_pads.blocks.pads.ThinGravityPadPush;

import static xyz.aikoyori.gravity_pads.GravityPads.GRAVITY_PADS_ITEMS_GROUP;

public class GPBlocks implements BlockRegistryContainer {
	public static final DirectionalGravityBlock DIRECTIONAL_GRAVITY_BLOCK = new DirectionalGravityBlock(QuiltBlockSettings.of(Material.METAL).hardness(1.0f).nonOpaque());
	@Override
	public BlockItem createBlockItem(Block block, String identifier) {
		return new BlockItem(block, new OwoItemSettings().group(GRAVITY_PADS_ITEMS_GROUP).tab(1));
	}
}
