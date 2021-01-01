package dev.onyxstudios.bff;

import dev.onyxstudios.bff.registry.ModBlocks;
import dev.onyxstudios.bff.registry.ModEntitites;
import dev.onyxstudios.bff.registry.ModRenders;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("bff")
public class BFF {

    public static final String MODID = "bff";
    public static ItemGroup modItemGroup = new ItemGroup(MODID) {
        @Override
        public ItemStack createIcon() {
            return ModBlocks.treeGoniaFloatingItem.get().getDefaultInstance();
        }
    };

    public BFF() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        ModBlocks.blockRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.itemRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntitites.tilesRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void setup(FMLCommonSetupEvent event) {
    }

    private void setupClient(FMLClientSetupEvent event) {
        ModRenders.registerRenders();
    }
}
