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

public abstract class AbstractThinGravityPadBlock extends AbstractGravityPadBlock{


	public AbstractThinGravityPadBlock(Settings settings) {
		super(settings);
	}
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		switch (state.get(DIRECTION))
		{

			case DOWN -> {
				return THIN_OUT_POINT_DOWN_SHAPE;
			}
			case UP -> {
				return THIN_OUT_POINT_UP_SHAPE;
			}
			case NORTH -> {
				return THIN_OUT_POINT_NORTH_SHAPE;
			}
			case SOUTH -> {
				return THIN_OUT_POINT_SOUTH_SHAPE;
			}
			case WEST -> {
				return THIN_OUT_POINT_WEST_SHAPE;
			}
			case EAST -> {
				return THIN_OUT_POINT_EAST_SHAPE;
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
		switch (state.get(DIRECTION))
		{

			case DOWN -> {
				return THIN_POINT_DOWN_SHAPE;
			}
			case UP -> {
				return THIN_POINT_UP_SHAPE;
			}
			case NORTH -> {
				return THIN_POINT_NORTH_SHAPE;
			}
			case SOUTH -> {
				return THIN_POINT_SOUTH_SHAPE;
			}
			case WEST -> {
				return THIN_POINT_WEST_SHAPE;
			}
			case EAST -> {
				return THIN_POINT_EAST_SHAPE;
			}
		}
		return THIN_POINT_UP_SHAPE;
	}


}
