package dev.onyxstudios.bff.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;
import vazkii.botania.common.block.BlockFloatingSpecialFlower;

import java.util.function.Supplier;

public class TankFloatingFlower extends BlockFloatingSpecialFlower {

    public TankFloatingFlower(Properties props, Supplier<? extends TileEntitySpecialFlower> teProvider) {
        super(props, teProvider);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        TankFunctionalFlower.interactWithFlower(world, pos, player, hand);
        return ActionResultType.func_233537_a_(world.isRemote);
    }
}
