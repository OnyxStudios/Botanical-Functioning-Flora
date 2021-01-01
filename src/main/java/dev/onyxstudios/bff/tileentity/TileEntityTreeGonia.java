package dev.onyxstudios.bff.tileentity;

import dev.onyxstudios.bff.registry.ModEntitites;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.TileEntityFunctionalFlower;

public class TileEntityTreeGonia extends TileEntityFunctionalFlower {

    public static final int RANGE = 8;
    public static final int MANA_USAGE = 25;
    public int maxWorkTime = 20;
    public int workTime = 0;

    public TileEntityTreeGonia() {
        super(ModEntitites.treeGoniaType.get());
    }

    @Override
    public void tickFlower() {
        super.tickFlower();

        if(!world.isRemote && redstoneSignal <= 0 && getMana() >= MANA_USAGE) {
            workTime++;
            if(workTime >= maxWorkTime) {
                Iterable<BlockPos> iterable = BlockPos.getAllInBoxMutable(pos.add(-RANGE, 0, -RANGE), pos.add(RANGE, 16, RANGE));
                for (BlockPos workPos : iterable) {
                    if(world.getBlockState(workPos).isIn(BlockTags.LOGS)) {
                        BlockPos immutablePos = workPos.toImmutable();
                        world.destroyBlock(immutablePos, true);
                        this.addMana(-MANA_USAGE);
                        break;
                    }
                }

                workTime = 0;
            }
        }
    }

    @Override
    public boolean acceptsRedstone() {
        return true;
    }

    @Override
    public RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Square(getEffectivePos(), RANGE);
    }

    @Override
    public int getMaxMana() {
        return 100;
    }

    @Override
    public int getColor() {
        return 7980079;
    }
}
