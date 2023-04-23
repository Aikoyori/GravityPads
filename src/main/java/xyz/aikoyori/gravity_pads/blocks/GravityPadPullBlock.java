package xyz.aikoyori.gravity_pads.blocks;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.Gravity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;

public class GravityPadPullBlock extends AbstractGravityPadBlock{
	public GravityPadPullBlock(Settings settings) {
		super(settings);
	}


	@Override

	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		try{
			boolean hello = (state.get(DIRECTION) == GravityChangerAPI.getGravityDirection(entity));
			boolean isAlreadyOnThatSide = (state.get(DIRECTION).getOpposite() == GravityChangerAPI.getGravityDirection(entity));
			if(!world.isClient() && !isAlreadyOnThatSide)
			{

				GravityChangerAPI.setDefaultGravityDirection(entity,state.get(DIRECTION).getOpposite(),new RotationParameters().alternateCenter(true).rotateVelocity(true).rotationTime(450));
				entity.fallDistance = 0;
			}

			if(hello)
			{
				Vec3d hi = entity.getPos().add(Vec3d.of(state.get(DIRECTION).getOpposite().getVector()).multiply(entity.getHeight()));
				//entity.setPosition(hi);
				//entity.updatePosition(hi.getX(),hi.getY(),hi.getZ());
			}
		}
		catch(Exception x)
		{

		}

		super.onEntityCollision(state, world, pos, entity);
	}
}
