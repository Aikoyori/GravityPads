package xyz.aikoyori.gravity_pads.blocks.pads;

import com.fusionflux.gravity_api.api.GravityChangerAPI;
import com.fusionflux.gravity_api.api.RotationParameters;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.gravity_pads.GravityPads;
import xyz.aikoyori.gravity_pads.blocks.abstracted.AbstractNormalGravityPadBlock;
import xyz.aikoyori.gravity_pads.config.GravityPadConfigModel;
import xyz.aikoyori.gravity_pads.utils.Constants;

import java.util.function.Predicate;

public class DirectionalGravityPad extends AbstractNormalGravityPadBlock {
	public DirectionalGravityPad(Settings settings) {
		super(settings);
	}

	public static final DirectionProperty GRAVITY_DIRECTION = DirectionProperty.of("gravity_direction");

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		try{
			boolean isAlreadyOnThatSide = (state.get(GRAVITY_DIRECTION) == GravityChangerAPI.getGravityDirection(entity));
			if(!world.isClient() && !isAlreadyOnThatSide)
			{
				GravityChangerAPI.setDefaultGravityDirection(entity,state.get(GRAVITY_DIRECTION));
				entity.fallDistance = 0;
			}
		}
		catch(Exception x)
		{

		}
		super.onEntityCollision(state, world, pos, entity);
	}


	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

			if(Constants.canPlayerRotate(player, hand)){
				world.setBlockState(pos,state.with(GRAVITY_DIRECTION,rotateDirection(state.get(GRAVITY_DIRECTION),hit.getSide(),player.isSneaking())));

				return ActionResult.SUCCESS;
			}

		return super.onUse(state, world, pos, player, hand, hit);
	}

	private Direction rotateDirection(Direction oldDir,Direction upSide, boolean backwards)
	{
		return backwards?oldDir.rotateCounterclockwise(upSide.getAxis()):oldDir.rotateClockwise(upSide.getAxis());
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		int placementSide = Constants.getPlacementRegion(ctx.getHitPos(),ctx.getSide());
		Direction gravityDirection = Constants.getGravitySide(ctx.getSide(),placementSide);
		if(ctx.getPlayer().isSneaking()) gravityDirection = gravityDirection.getOpposite();
		return super.getPlacementState(ctx).with(GRAVITY_DIRECTION,gravityDirection);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(GRAVITY_DIRECTION);
	}
}
