package dev.onyxstudios.bff.registry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import vazkii.botania.client.render.tile.RenderTileSpecialFlower;

public class ModRenders {

    public static void registerRenders() {
        ClientRegistry.bindTileEntityRenderer(ModEntitites.treeGoniaType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.treeGonia.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.treeGoniaFloating.get(), RenderType.getCutout());
    }
}
