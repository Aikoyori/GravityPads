package xyz.aikoyori.gravity_pads;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.registration.reflect.FieldRegistrationHandler;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.aikoyori.gravity_pads.config.GravityPadsConfig;
import xyz.aikoyori.gravity_pads.registry.GPBlocks;
import xyz.aikoyori.gravity_pads.registry.GPPads;

public class GravityPads implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("Gravity Pads");
	public static final String MOD_ID = "gravity_pads";
	public static final OwoItemGroup GRAVITY_PADS_ITEMS_GROUP = OwoItemGroup.builder(makeId("gravity_pads"),()->
			Icon.of(new ItemStack(GPPads.GRAVITY_PAD_PULL))).initializer(owoItemGroup -> {
			owoItemGroup.addTab(Icon.of(new ItemStack(GPPads.GRAVITY_PAD_PUSH.asItem(),1)),"pads",null,false);
			owoItemGroup.addTab(Icon.of(new ItemStack(GPBlocks.DIRECTIONAL_GRAVITY_BLOCK.asItem(),1)),"blocks",null,false);
	}).build();

	public static final GravityPadsConfig gravityPadConfig = GravityPadsConfig.createAndLoad();
	public static final TagKey<Item> DIRECTION_CHANGER = TagKey.of(Registries.ITEM.getKey(), new Identifier(MOD_ID, "direction_changer"));
	@Override
	public void onInitialize(ModContainer mod) {
		MixinExtrasBootstrap.init();
		FieldRegistrationHandler.register(GPPads.class,MOD_ID,false);
		FieldRegistrationHandler.register(GPBlocks.class,MOD_ID,false);
		GRAVITY_PADS_ITEMS_GROUP.initialize();
		//LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
	}

	public static Identifier makeId(String name)
	{
		return new Identifier(MOD_ID,name);
	}
}
