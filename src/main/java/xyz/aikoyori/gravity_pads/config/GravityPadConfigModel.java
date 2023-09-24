package xyz.aikoyori.gravity_pads.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;
import io.wispforest.owo.config.annotation.Sync;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.aikoyori.gravity_pads.GravityPads;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Modmenu(modId = GravityPads.MOD_ID)
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@Config(name = "gravity-pads", wrapperName = "GravityPadsConfig")
public class GravityPadConfigModel {
	@SectionHeader("gameplay")
	public DirectionChangeMode directionChangeMode = DirectionChangeMode.TAG;
	public String directionChanger = "minecraft:stick";
	@SectionHeader("up-gravity")
	public boolean antiSoftlock = true;

	public int antiSoftlockHeight = 64;
	public AntiSoftLockMethod antiSoftlockMethod = AntiSoftLockMethod.RESET_GRAVITY;
	@SectionHeader("side-gravity")
	public boolean antiSoftlockSide = false;
	public int antiSoftlockSideFallDistance = 384;
	public AntiSoftLockMethod antiSoftlockSideMethod = AntiSoftLockMethod.RESET_GRAVITY;
	@SectionHeader("experimental")
	public boolean enablePlacementHelper = true;
	public enum DirectionChangeMode{
		TAG,CUSTOM_ITEM,EMPTY_HAND,ANY,NONE,
	}

	public enum AntiSoftLockMethod{
		RESET_GRAVITY,TICK_VOID,INSTANT_DEATH,
	}

}
