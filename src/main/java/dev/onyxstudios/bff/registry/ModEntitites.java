package dev.onyxstudios.bff.registry;

import dev.onyxstudios.bff.BFF;
import dev.onyxstudios.bff.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitites {

    public static final DeferredRegister<TileEntityType<?>> tilesRegistry = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BFF.MODID);

    public static RegistryObject<TileEntityType<TileEntityTreegonia>> treeGoniaType = tilesRegistry.register("treegonia_tile", () -> TileEntityType.Builder.create(TileEntityTreegonia::new, ModBlocks.treeGonia.get(), ModBlocks.treeGoniaFloating.get()).build(null));
    public static RegistryObject<TileEntityType<TileEntityPulverose>> pulveroseType = tilesRegistry.register("pulverose_tile", () -> TileEntityType.Builder.create(TileEntityPulverose::new, ModBlocks.pulverose.get(), ModBlocks.pulveroseFloating.get()).build(null));
    public static RegistryObject<TileEntityType<TileEntityGreedaffodil>> greedaffodilType = tilesRegistry.register("greedaffidil_tile", () -> TileEntityType.Builder.create(TileEntityGreedaffodil::new, ModBlocks.greedaffodil.get(), ModBlocks.greedaffodilFloating.get()).build(null));
    public static RegistryObject<TileEntityType<TileEntityThirstillium>> thirstilliumType = tilesRegistry.register("thirstillium_tile", () -> TileEntityType.Builder.create(TileEntityThirstillium::new, ModBlocks.thirstillium.get(), ModBlocks.thirstilliumFloating.get()).build(null));
    public static RegistryObject<TileEntityType<TileEntityLingfeiThiefily>> lingfeiThiefilyType = tilesRegistry.register("lingfei_thiefily_tile", () -> TileEntityType.Builder.create(TileEntityLingfeiThiefily::new, ModBlocks.lingfeiThiefily.get(), ModBlocks.lingfeiThiefilyFloating.get()).build(null));
}
