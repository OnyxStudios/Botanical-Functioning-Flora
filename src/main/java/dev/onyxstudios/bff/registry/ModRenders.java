package dev.onyxstudios.bff.registry;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.botania.client.render.tile.RenderTileSpecialFlower;

public class ModRenders {

    public static void registerRenders(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ModEntitites.treeGoniaType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.treeGonia.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.treeGoniaFloating.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(ModEntitites.pulveroseType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.pulverose.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.pulveroseFloating.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(ModEntitites.greedaffodilType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.greedaffodil.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.greedaffodilFloating.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(ModEntitites.thirstilliumType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.thirstillium.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.thirstilliumFloating.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(ModEntitites.lingfeiThiefilyType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.lingfeiThiefily.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.lingfeiThiefilyFloating.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(ModEntitites.honeysuckleType.get(), RenderTileSpecialFlower::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.honeysuckle.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(ModBlocks.honeysuckleFloating.get(), RenderType.getCutout());

        event.getMinecraftSupplier().get().getItemColors().register((itemStack, tintIndex) -> 0xE2C0AA, ModItems.ironDust.get());
        event.getMinecraftSupplier().get().getItemColors().register((itemStack, tintIndex) -> 0xFCEE4B, ModItems.goldDust.get());
    }
}
