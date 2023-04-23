package xyz.aikoyori.gravity_pads.mixin;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.util.Gravity;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.aikoyori.gravity_pads.GravityPads;

@Mixin(Entity.class)
public class SpaceDamageMixin {
	@Inject(method = "attemptTickInVoid", at = @At("HEAD"))
	public void gravitypads$spacedamage(CallbackInfo ci) {
			Entity ent = (Entity)(Object)this;

			if (GravityChangerAPI.getActualGravityDirection(ent) == Direction.UP && ent.getY() > (double)(ent.world.getTopY() + ent.world.getGameRules().getInt(GravityPads.FALLUP_HEIGHT_DAMAGE))) {
				//ent.tickInVoid();
				GravityChangerAPI.setDefaultGravityDirection(ent,Direction.DOWN);
			}
		//GravityPads.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
