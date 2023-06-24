package xyz.aikoyori.gravity_pads;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.aikoyori.gravity_pads.blocks.AbstractGravityPadBlock;
import xyz.aikoyori.gravity_pads.blocks.GravityPadPullBlock;
import xyz.aikoyori.gravity_pads.blocks.GravityPadPushBlock;
import xyz.aikoyori.gravity_pads.config.GravityPadConfigModel;
import xyz.aikoyori.gravity_pads.config.GravityPadsConfig;

public class GravityPads implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Gravity Pads");
	public static final String MOD_ID = "gravity_pads";

	public static final GravityPadsConfig gravityPadConfig = GravityPadsConfig.createAndLoad();
	public static final GameRules.Key<GameRules.IntRule> FALLUP_HEIGHT_DAMAGE =
			GameRuleRegistry.register("fallupHeightDamageAfterWorldHeight", GameRules.Category.MISC, GameRuleFactory.createIntRule(64));
	public static final AbstractGravityPadBlock GRAVITY_PAD_PULL = new GravityPadPullBlock(QuiltBlockSettings.of(Material.METAL).hardness(1.0f));
	public static final AbstractGravityPadBlock GRAVITY_PAD_PUSH = new GravityPadPushBlock(QuiltBlockSettings.of(Material.METAL).hardness(1.0f));
	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registry.BLOCK,makeId("gravity_pad_pull"),GRAVITY_PAD_PULL);
		Registry.register(Registry.ITEM,makeId("gravity_pad_pull"), new BlockItem(GRAVITY_PAD_PULL, new QuiltItemSettings().group(ItemGroup.TRANSPORTATION)));
		Registry.register(Registry.BLOCK,makeId("gravity_pad_push"),GRAVITY_PAD_PUSH);
		Registry.register(Registry.ITEM,makeId("gravity_pad_push"), new BlockItem(GRAVITY_PAD_PUSH, new QuiltItemSettings().group(ItemGroup.TRANSPORTATION)));
		//LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
	}

	public static Identifier makeId(String name)
	{
		return new Identifier(MOD_ID,name);
	}
}
