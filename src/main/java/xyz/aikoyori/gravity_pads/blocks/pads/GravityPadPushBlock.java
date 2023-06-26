package xyz.aikoyori.gravity_pads.blocks.pads;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.api.RotationParameters;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.aikoyori.gravity_pads.blocks.abstracted.AbstractGravityPadBlock;
import xyz.aikoyori.gravity_pads.blocks.abstracted.AbstractNormalGravityPadBlock;

public class GravityPadPushBlock extends AbstractNormalGravityPadBlock {
	public GravityPadPushBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		try{
			boolean isAlreadyOnThatSide = (state.get(DIRECTION) == GravityChangerAPI.getGravityDirection(entity));
			if(!world.isClient() && !isAlreadyOnThatSide)
			{
				GravityChangerAPI.setDefaultGravityDirection(entity,state.get(DIRECTION),new RotationParameters().alternateCenter(true).rotateVelocity(true).rotationTime(450));
				entity.fallDistance = 0;
			}
		}
		catch(Exception x)
		{

		}
		super.onEntityCollision(state, world, pos, entity);
	}
}
