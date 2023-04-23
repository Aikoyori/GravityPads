package xyz.aikoyori.gravity_pads.blocks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public abstract class AbstractGravityPadBlock extends Block implements Waterloggable {

	public static final DirectionProperty DIRECTION = DirectionProperty.of("direction");

	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	private static final VoxelShape POINT_DOWN_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape POINT_UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
	private static final VoxelShape POINT_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	private static final VoxelShape POINT_WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	private static final VoxelShape POINT_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	private static final VoxelShape POINT_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
	public AbstractGravityPadBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(DIRECTION, Direction.UP).with(WATERLOGGED, false));
	}
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if ((Boolean)state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	public FluidState getFluidState(BlockState state) {
		return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());

		return this.getDefaultState().with(DIRECTION,ctx.getSide()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return super.getCollisionShape(state, world, pos, context);
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
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(DIRECTION);
		builder.add(WATERLOGGED);
	}
}
