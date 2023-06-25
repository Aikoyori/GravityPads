package xyz.aikoyori.gravity_pads.blocks.abstracted;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.function.Function;

import static xyz.aikoyori.gravity_pads.utils.Constants.*;

public abstract class AbstractNormalGravityPadBlock extends AbstractGravityPadBlock{


	public AbstractNormalGravityPadBlock(Settings settings) {
		super(settings);
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(DIRECTION))
		{

			case DOWN -> {
				return POINT_DOWN_SHAPE;
			}
			case UP -> {
				return POINT_UP_SHAPE;
			}
			case NORTH -> {
				return POINT_NORTH_SHAPE;
			}
			case SOUTH -> {
				return POINT_SOUTH_SHAPE;
			}
			case WEST -> {
				return POINT_WEST_SHAPE;
			}
			case EAST -> {
				return POINT_EAST_SHAPE;
			}
		}

		return POINT_UP_SHAPE;
	}
	@Override
	protected ImmutableMap<BlockState, VoxelShape> getShapesForStates(Function<BlockState, VoxelShape> stateToShape) {
		return super.getShapesForStates(stateToShape);
	}
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getOutlineShape(state,world,pos,context);
	}

}
