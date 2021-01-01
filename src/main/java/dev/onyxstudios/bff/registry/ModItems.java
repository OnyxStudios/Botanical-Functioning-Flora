package dev.onyxstudios.bff.registry;

import dev.onyxstudios.bff.BFF;
import dev.onyxstudios.bff.items.DustItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> itemRegistry = DeferredRegister.create(ForgeRegistries.ITEMS, BFF.MODID);

    public static RegistryObject<Item> ironDust = itemRegistry.register("iron_dust", () -> new DustItem());
    public static RegistryObject<Item> goldDust = itemRegistry.register("gold_dust", () -> new DustItem());
}
