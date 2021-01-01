package dev.onyxstudios.bff.blocks;

import net.minecraft.potion.Effect;
import vazkii.botania.api.subtile.TileEntitySpecialFlower;
import vazkii.botania.common.block.BlockSpecialFlower;

import java.util.function.Supplier;

public class BlockFunctionalFlower extends BlockSpecialFlower {

    public BlockFunctionalFlower(Effect stewEffect, int stewDuration, Properties props, Supplier<? extends TileEntitySpecialFlower> teProvider) {
        super(stewEffect, stewDuration, props, teProvider);
    }
}
