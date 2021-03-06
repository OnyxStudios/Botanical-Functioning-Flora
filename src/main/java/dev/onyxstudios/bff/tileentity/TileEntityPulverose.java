package dev.onyxstudios.bff.tileentity;

import dev.onyxstudios.bff.registry.ModEntitites;
import dev.onyxstudios.bff.registry.ModRecipes;
import dev.onyxstudios.bff.registry.recipe.PulveroseRecipe;
import dev.onyxstudios.bff.utils.PulveroseInvWrapper;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;
import vazkii.botania.client.fx.SparkleParticleData;
import vazkii.botania.client.fx.WispParticleData;

import java.util.Optional;

/**
 * Pulverose Flower
 * Flower acts similar a PureDaisy
 * Any Ore placed in it's 3x3 radius will be pulverized down to a dust, or doubled depending on the recipe
 * Dust can be smelted back to ingots
 */
public class TileEntityPulverose extends TileEntityFunctionalFlower {

    public static final BlockPos[] POSITIONS = new BlockPos[] {new BlockPos(-1, 0, -1), new BlockPos(-1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(0, 0, 1), new BlockPos(1, 0, 1), new BlockPos(1, 0, 0), new BlockPos(1, 0, -1), new BlockPos(0, 0, -1)};
    public int[] workProgress = new int[8];
    public int maxWorkProgress = 20;
    public int currentPosition = 0;
    public PulveroseInvWrapper wrapper = new PulveroseInvWrapper();

    public TileEntityPulverose() {
        super(ModEntitites.pulveroseType.get());
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if (world.isRemote())
            return;

        BlockPos currentPos = pos.add(POSITIONS[currentPosition]);
        if (!world.isAirBlock(currentPos) && world.getTileEntity(currentPos) == null) {
            wrapper.setInventorySlotContents(0, world.getBlockState(currentPos).getBlock().asItem().getDefaultInstance());
            Optional<IRecipe<PulveroseInvWrapper>> recipeOptional = world.getRecipeManager().getRecipe(ModRecipes.PULVEROSE, wrapper, world);

            recipeOptional.ifPresent(recipe -> {
                if (getMana() >= ((PulveroseRecipe) recipe).getCost()) {
                    workProgress[currentPosition]++;
                    if (workProgress[currentPosition] >= maxWorkProgress) {
                        world.destroyBlock(currentPos, false);
                        InventoryHelper.spawnItemStack(world, currentPos.getX(), currentPos.getY(), currentPos.getZ(), recipe.getRecipeOutput().copy());
                        addMana(-((PulveroseRecipe) recipe).getCost());
                        workProgress[currentPosition] = 0;
                    }
                }
            });
        }

        currentPosition++;
        if (currentPosition > 7)
            currentPosition = 0;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(this.getEffectivePos(), 1);
    }

    @Override
    public int getColor() {
        return 4144959;
    }

    @Override
    public int getMaxMana() {
        return 20000;
    }
}
