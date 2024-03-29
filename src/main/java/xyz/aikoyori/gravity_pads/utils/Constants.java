package xyz.aikoyori.gravity_pads.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.aikoyori.gravity_pads.GravityPads;
import xyz.aikoyori.gravity_pads.blocks.blocks.DirectionalGravityBlock;
import xyz.aikoyori.gravity_pads.blocks.pads.DirectionalGravityPad;
import xyz.aikoyori.gravity_pads.blocks.pads.ThinDirectionalGravityPad;
import xyz.aikoyori.gravity_pads.config.GravityPadConfigModel;
import xyz.aikoyori.gravity_pads.registry.GPBlocks;
import xyz.aikoyori.gravity_pads.registry.GPPads;

public class Constants {
	public static final VoxelShape POINT_DOWN_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
	public static final VoxelShape POINT_UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
	public static final VoxelShape POINT_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
	public static final VoxelShape POINT_WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	public static final VoxelShape POINT_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
	public static final VoxelShape POINT_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
	// for thin focus to differentiate it from the thick one
	public static final VoxelShape THIN_OUT_POINT_DOWN_SHAPE = Block.createCuboidShape(0.0, 15.9, 0.0, 16.0, 16.0, 16.0);
	public static final VoxelShape THIN_OUT_POINT_UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 0.1, 16.0);
	public static final VoxelShape THIN_OUT_POINT_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 0.1, 16.0, 16.0);
	public static final VoxelShape THIN_OUT_POINT_WEST_SHAPE = Block.createCuboidShape(15.9, 0.0, 0.0, 16.0, 16.0, 16.0);
	public static final VoxelShape THIN_OUT_POINT_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 0.1);
	public static final VoxelShape THIN_OUT_POINT_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.9, 16.0, 16.0, 16.0);
	public static final VoxelShape THIN_POINT_DOWN_SHAPE = Block.createCuboidShape(0.0, 16.0, 0.0, 16.0, 16.00001, 16.0);
	public static final VoxelShape THIN_POINT_UP_SHAPE = Block.createCuboidShape(0.0, -0.001, 0.0, 16.0, 0.0, 16.0);
	public static final VoxelShape THIN_POINT_EAST_SHAPE = Block.createCuboidShape(-0.001, 0.0, 0.0, 0.0, 16.0, 16.0);
	public static final VoxelShape THIN_POINT_WEST_SHAPE = Block.createCuboidShape(16.0, 0.0, 0.0, 16.001, 16.0, 16.0);
	public static final VoxelShape THIN_POINT_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, -0.001, 16.0, 16.0, 0.0);
	public static final VoxelShape THIN_POINT_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 16.0, 16.0, 16.0, 16.001);
	public static int getPlacementRegion(@NotNull Vec3d hitLocation, Direction plateFacing){
		Vec3d graph0 = new Vec3d(
				((hitLocation.getX()-(int)hitLocation.getX())),
				((hitLocation.getY()-(int)hitLocation.getY())),
				((hitLocation.getZ()-(int)hitLocation.getZ()))
		);
		Vec3d graph = new Vec3d(
				((graph0.getX()>0?graph0.getX():graph0.getX()+1)-0.5),
				((graph0.getY()>0?graph0.getY():graph0.getY()+1)-0.5),
				((graph0.getZ()>0?graph0.getZ():graph0.getZ()+1)-0.5)
		);
		//GravityPads.LOGGER.info(""+graph);
		double x = 0,y = 0;
		switch (plateFacing)
		{
			case DOWN: case UP:
				x = graph.x;
				y = graph.z;
				break;
			case EAST: case WEST:
				x = graph.y;
				y = graph.z;
				break;
			case SOUTH: case NORTH:
				x = graph.x;
				y = graph.y;
				break;
		}
		//GravityPads.LOGGER.info("The "+x+","+y);
		if(Math.abs(x)<0.2f&&Math.abs(y)<0.2f) return 0;
		if(x-y>0&&x+y>0) return 1;
		if(x-y<0&&x+y>0) return 2;
		if(x-y<0&&x+y<0) return 3;
		if(x-y>0&&x+y<0) return 4;
		return 0;
	}
	public static Direction getGravitySideAffectedByPlayer(Direction side, int placementSide, PlayerEntity player)
	{
		Direction dir = getGravitySide(side,placementSide);
		if(player.isSneaky()) dir = dir.getOpposite();
		return dir;
	}
	public static Direction getGravitySide(Direction side, int placementSide){
		if(placementSide==0)
		{
			return side;
		}
		return switch (side){

			case UP, DOWN -> {
				switch(placementSide){
					case 1 -> {yield Direction.EAST;}
					case 2 -> {yield Direction.SOUTH;}
					case 3 -> {yield Direction.WEST;}
					case 4 -> {yield Direction.NORTH;}
					default -> {yield Direction.EAST;}
				}
			}
			case NORTH,SOUTH -> {
				switch(placementSide){
					case 1 -> {yield Direction.EAST;}
					case 2 -> {yield Direction.UP;}
					case 3 -> {yield Direction.WEST;}
					case 4 -> {yield Direction.DOWN;}
					default -> {yield Direction.UP;}
				}
			}
			case EAST,WEST -> {
				switch(placementSide){
					case 1 -> {yield Direction.UP;}
					case 2 -> {yield Direction.SOUTH;}
					case 3 -> {yield Direction.DOWN;}
					case 4 -> {yield Direction.NORTH;}
					default -> {yield Direction.UP;}
				}
			}
		};
	}
	public static boolean canPlayerRotate(PlayerEntity player, Hand hand)
	{
		return (player.getAbilities().allowModifyWorld &&(
				GravityPads.gravityPadConfig.directionChangeMode()== GravityPadConfigModel.DirectionChangeMode.ANY ||
						(GravityPads.gravityPadConfig.directionChangeMode()==GravityPadConfigModel.DirectionChangeMode.CUSTOM_ITEM && player.getStackInHand(hand).getItem() == Registries.ITEM.get(Identifier.splitOn(GravityPads.gravityPadConfig.directionChanger(),":".charAt(0)))) ||
						(GravityPads.gravityPadConfig.directionChangeMode()==GravityPadConfigModel.DirectionChangeMode.EMPTY_HAND && player.getStackInHand(Hand.MAIN_HAND).isEmpty()) ||
						(GravityPads.gravityPadConfig.directionChangeMode()==GravityPadConfigModel.DirectionChangeMode.TAG && player.getStackInHand(Hand.MAIN_HAND).isIn(GravityPads.DIRECTION_CHANGER))
		));
	}

	@Nullable
	public static BlockState alignmentHelperType(ItemStack stack, BlockHitResult bhr, PlayerEntity player)
	{
		if(stack.getItem() == GPPads.DIRECTIONAL_GRAVITY_PAD.asItem())
		{
			int placementSide = getPlacementRegion(bhr.getPos(),bhr.getSide());
			Direction gravityDirection = getGravitySideAffectedByPlayer(bhr.getSide(),placementSide,player);
			return GPPads.DIRECTIONAL_GRAVITY_PAD.getDefaultState().with(DirectionalGravityPad.DIRECTION,bhr.getSide()).with(DirectionalGravityPad.GRAVITY_DIRECTION,gravityDirection);

		}
		if(stack.getItem() == GPPads.THIN_DIRECTIONAL_GRAVITY_PAD.asItem())
		{
			int placementSide = getPlacementRegion(bhr.getPos(),bhr.getSide());
			Direction gravityDirection = getGravitySideAffectedByPlayer(bhr.getSide(),placementSide,player);
			return GPPads.THIN_DIRECTIONAL_GRAVITY_PAD.getDefaultState().with(ThinDirectionalGravityPad.DIRECTION,bhr.getSide()).with(ThinDirectionalGravityPad.GRAVITY_DIRECTION,gravityDirection);
		}
		if(stack.getItem() == GPBlocks.DIRECTIONAL_GRAVITY_BLOCK.asItem())
		{
			int placementSide = getPlacementRegion(bhr.getPos(),bhr.getSide());
			Direction gravityDirection = getGravitySideAffectedByPlayer(bhr.getSide(),placementSide,player);
			return GPBlocks.DIRECTIONAL_GRAVITY_BLOCK.getDefaultState().with(DirectionalGravityBlock.GRAVITY_DIRECTION,gravityDirection);
		}
		return null;
	}

}
