package xyz.aikoyori.gravity_pads.blocks;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.api.RotationParameters;
import com.fusionflux.gravity_api.util.Gravity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;

public class GravityPadPushBlock extends AbstractGravityPadBlock{
	public GravityPadPushBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		try{
			boolean hello = (state.get(DIRECTION) == GravityChangerAPI.getGravityDirection(entity).getOpposite());
			boolean isAlreadyOnThatSide = (state.get(DIRECTION) == GravityChangerAPI.getGravityDirection(entity));
			if(!world.isClient() && !isAlreadyOnThatSide)
			{
				GravityChangerAPI.setDefaultGravityDirection(entity,state.get(DIRECTION),new RotationParameters().alternateCenter(true).rotateVelocity(true).rotationTime(450));
				entity.fallDistance = 0;
			}
			if(hello)
			{
				//entity.setPosition(entity.getPos().add(Vec3d.of(state.get(DIRECTION).getVector()).multiply(entity.getHeight())));
				//entity.updatePosition(entity.getPos().getX(),entity.getPos().getY(),entity.getPos().getZ());
			}
		}
		catch(Exception x)
		{

		}
		super.onEntityCollision(state, world, pos, entity);
	}
}
