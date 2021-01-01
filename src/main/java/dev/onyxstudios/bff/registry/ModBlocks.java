package dev.onyxstudios.bff.registry;

import dev.onyxstudios.bff.BFF;
import dev.onyxstudios.bff.blocks.BlockFunctionalFlower;
import dev.onyxstudios.bff.tileentity.TileEntityGreedaffodil;
import dev.onyxstudios.bff.tileentity.TileEntityPulverose;
import dev.onyxstudios.bff.tileentity.TileEntityTreegonia;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import vazkii.botania.common.block.BlockFloatingSpecialFlower;
import vazkii.botania.common.brew.ModPotions;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static final DeferredRegister<Block> blockRegistry = DeferredRegister.create(ForgeRegistries.BLOCKS, BFF.MODID);
    public static final DeferredRegister<Item> itemRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, BFF.MODID);

    public static AbstractBlock.Properties FLOWER_PROPS = AbstractBlock.Properties.from(Blocks.POPPY);
    public static Item.Properties FLOWER_ITEM_PROPS = new Item.Properties().group(BFF.modItemGroup);

    public static RegistryObject<BlockFunctionalFlower> treeGonia = blockRegistry.register("treegonia", () -> new BlockFunctionalFlower(ModPotions.clear, 0, FLOWER_PROPS, TileEntityTreegonia::new));
    public static RegistryObject<BlockItem> treeGoniaItem = itemRegistry.register("treegonia", () -> new ItemBlockSpecialFlower(treeGonia.get(), FLOWER_ITEM_PROPS));

    public static RegistryObject<BlockFloatingSpecialFlower> treeGoniaFloating = blockRegistry.register("floating_treegonia", () -> new BlockFloatingSpecialFlower(vazkii.botania.common.block.ModBlocks.FLOATING_PROPS, TileEntityTreegonia::new));
    public static RegistryObject<BlockItem> treeGoniaFloatingItem = itemRegistry.register("floating_treegonia", () -> new ItemBlockSpecialFlower(treeGoniaFloating.get(), FLOWER_ITEM_PROPS));

    public static RegistryObject<BlockFunctionalFlower> pulverose = blockRegistry.register("pulverose", () -> new BlockFunctionalFlower(ModPotions.clear, 0, FLOWER_PROPS, TileEntityPulverose::new));
    public static RegistryObject<BlockItem> pulveroseItem = itemRegistry.register("pulverose", () -> new ItemBlockSpecialFlower(pulverose.get(), FLOWER_ITEM_PROPS));

    public static RegistryObject<BlockFloatingSpecialFlower> pulveroseFloating = blockRegistry.register("floating_pulverose", () -> new BlockFloatingSpecialFlower(vazkii.botania.common.block.ModBlocks.FLOATING_PROPS, TileEntityPulverose::new));
    public static RegistryObject<BlockItem> pulveroseFloatingItem = itemRegistry.register("floating_pulverose", () -> new ItemBlockSpecialFlower(pulveroseFloating.get(), FLOWER_ITEM_PROPS));

    public static RegistryObject<BlockFunctionalFlower> greedaffodil = blockRegistry.register("greedaffodil", () -> new BlockFunctionalFlower(ModPotions.clear, 0, FLOWER_PROPS, TileEntityGreedaffodil::new));
    public static RegistryObject<BlockItem> greedaffodilItem = itemRegistry.register("greedaffodil", () -> new ItemBlockSpecialFlower(greedaffodil.get(), FLOWER_ITEM_PROPS));

    public static RegistryObject<BlockFloatingSpecialFlower> greedaffodilFloating = blockRegistry.register("floating_greedaffodil", () -> new BlockFloatingSpecialFlower(vazkii.botania.common.block.ModBlocks.FLOATING_PROPS, TileEntityGreedaffodil::new));
    public static RegistryObject<BlockItem> greedaffodilFloatingItem = itemRegistry.register("floating_greedaffodil", () -> new ItemBlockSpecialFlower(greedaffodilFloating.get(), FLOWER_ITEM_PROPS));
}
