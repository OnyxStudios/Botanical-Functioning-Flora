package dev.onyxstudios.bff.blocks;

import dev.onyxstudios.bff.tileentity.TileEntityThirstillium;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;

import java.util.function.Supplier;

public class TankFunctionalFlower extends BlockFunctionalFlower {

    public TankFunctionalFlower(Effect stewEffect, int stewDuration, Properties props, Supplier<? extends TileEntitySpecialFlower> teProvider) {
        super(stewEffect, stewDuration, props, teProvider);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        interactWithFlower(world, pos, player, hand);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    public static void interactWithFlower(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        if(!world.isRemote() && world.getTileEntity(pos) instanceof TileEntityThirstillium) {
            TileEntity tile = world.getTileEntity(pos);

            LazyOptional<IFluidHandler> fluidHandlerOptional = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
            fluidHandlerOptional.ifPresent(fluidHandler -> FluidUtil.interactWithFluidHandler(player, hand, fluidHandler));
            world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        }
    }
}
