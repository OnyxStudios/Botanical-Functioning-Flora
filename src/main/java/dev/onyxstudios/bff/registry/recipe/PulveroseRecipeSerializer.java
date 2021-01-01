package dev.onyxstudios.bff.registry.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class PulveroseRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PulveroseRecipe> {

    @Override
    public PulveroseRecipe read(ResourceLocation recipeId, JsonObject json) {
        String group = JSONUtils.getString(json, "group", "");
        ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
        int cost = JSONUtils.getInt(json, "cost");

        return new PulveroseRecipe(recipeId, group, result, ingredient, cost);
    }

    @Override
    public PulveroseRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        return new PulveroseRecipe(recipeId, buffer.readString(), buffer.readItemStack(), Ingredient.read(buffer), buffer.readInt());
    }

    @Override
    public void write(PacketBuffer buffer, PulveroseRecipe recipe) {
        buffer.writeString(recipe.group);
        buffer.writeItemStack(recipe.getRecipeOutput());
        recipe.ingredient.write(buffer);
        buffer.writeInt(recipe.cost);
    }
}
