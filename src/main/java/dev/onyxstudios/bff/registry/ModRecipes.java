package dev.onyxstudios.bff.registry;

import dev.onyxstudios.bff.BFF;
import dev.onyxstudios.bff.registry.recipe.PulveroseRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    public static final DeferredRegister<IRecipeSerializer<?>> recipesRegistry = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, BFF.MODID);

    public static RegistryObject<IRecipeSerializer<?>> PULVEROSE_SERIALIZER = recipesRegistry.register("pulverose", () -> new PulveroseRecipeSerializer());
    public static final IRecipeType PULVEROSE = IRecipeType.register(BFF.MODID + ":pulverose");
}
