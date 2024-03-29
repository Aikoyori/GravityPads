package xyz.aikoyori.gravity_pads.registry;


import io.wispforest.owo.itemgroup.OwoItemSettings;
import io.wispforest.owo.registration.reflect.BlockRegistryContainer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import xyz.aikoyori.gravity_pads.blocks.abstracted.AbstractGravityPadBlock;
import xyz.aikoyori.gravity_pads.blocks.pads.*;

import static xyz.aikoyori.gravity_pads.GravityPads.GRAVITY_PADS_ITEMS_GROUP;

public class GPPads implements BlockRegistryContainer {

	public static final AbstractGravityPadBlock GRAVITY_PAD_PULL = new GravityPadPullBlock(QuiltBlockSettings.create().sounds(BlockSoundGroup.COPPER).hardness(1.0f));
	public static final AbstractGravityPadBlock GRAVITY_PAD_PUSH = new GravityPadPushBlock(QuiltBlockSettings.create().sounds(BlockSoundGroup.METAL).hardness(1.0f));
	public static final AbstractGravityPadBlock THIN_GRAVITY_PAD_PULL = new ThinGravityPadPull(QuiltBlockSettings.create().sounds(BlockSoundGroup.COPPER).hardness(1.0f));
	public static final AbstractGravityPadBlock THIN_GRAVITY_PAD_PUSH = new ThinGravityPadPush(QuiltBlockSettings.create().sounds(BlockSoundGroup.METAL).hardness(1.0f));
	public static final AbstractGravityPadBlock DIRECTIONAL_GRAVITY_PAD = new DirectionalGravityPad(QuiltBlockSettings.create().sounds(BlockSoundGroup.ANCIENT_DEBRIS).hardness(1.0f));
	public static final AbstractGravityPadBlock THIN_DIRECTIONAL_GRAVITY_PAD = new ThinDirectionalGravityPad(QuiltBlockSettings.create().sounds(BlockSoundGroup.ANCIENT_DEBRIS).hardness(1.0f));
	@Override
	public BlockItem createBlockItem(Block block, String identifier) {
		return new BlockItem(block, new OwoItemSettings().group(GRAVITY_PADS_ITEMS_GROUP).tab(0));
	}
}
