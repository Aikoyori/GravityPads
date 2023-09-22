package xyz.aikoyori.gravity_pads.mixin;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.aikoyori.gravity_pads.GravityPads;

@Mixin(Entity.class)
public abstract class SpaceDamageMixin {
	@Shadow
	public abstract DamageSources getDamageSources();

	@Inject(method = "baseTick", at = @At("HEAD"))
	public void gravitypads$spacedamage(CallbackInfo ci) {
			Entity ent = (Entity)(Object)this;
			if(GravityPads.gravityPadConfig.antiSoftlock())
			if (GravityChangerAPI.getActualGravityDirection(ent) == Direction.UP && ent.getY() > (double)(ent.getWorld().getTopY() + GravityPads.gravityPadConfig.antiSoftlockHeight())) {
				//
				switch(GravityPads.gravityPadConfig.antiSoftlockMethod()){

					case RESET_GRAVITY -> {
						GravityChangerAPI.setDefaultGravityDirection(ent,Direction.DOWN);
					}
					case TICK_VOID -> {
						ent.tickInVoid();
					}
					case INSTANT_DEATH -> {
						ent.damage(getDamageSources().outOfWorld(),9999999);
					}
				}
			}
			if(GravityPads.gravityPadConfig.antiSoftlockSide())
			if (ent.fallDistance >= GravityPads.gravityPadConfig.antiSoftlockSideFallDistance() && GravityChangerAPI.getActualGravityDirection(ent) != Direction.UP && GravityChangerAPI.getActualGravityDirection(ent) != Direction.DOWN) {
				//
				switch(GravityPads.gravityPadConfig.antiSoftlockSideMethod()){

					case RESET_GRAVITY -> {
						GravityChangerAPI.setDefaultGravityDirection(ent,Direction.DOWN);
					}
					case TICK_VOID -> {
						ent.tickInVoid();
					}
					case INSTANT_DEATH -> {
						ent.damage(getDamageSources().outOfWorld(),9999999);
					}
				}
			}
		//GravityPads.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
