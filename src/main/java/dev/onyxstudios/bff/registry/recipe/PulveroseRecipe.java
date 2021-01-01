package dev.onyxstudios.bff.registry.recipe;

import dev.onyxstudios.bff.registry.ModRecipes;
import dev.onyxstudios.bff.utils.PulveroseInvWrapper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PulveroseRecipe implements IRecipe<PulveroseInvWrapper> {

    protected ResourceLocation ID;
    protected String group;
    protected ItemStack result;
    protected Ingredient ingredient;
    protected int cost;

    public PulveroseRecipe(ResourceLocation id, String group, ItemStack result, Ingredient ingredient, int cost) {
        this.ID = id;
        this.group = group;
        this.result = result;
        this.ingredient = ingredient;
        this.cost = cost;
    }

    @Override
    public boolean matches(PulveroseInvWrapper inv, World world) {
        if(inv.getSizeInventory() >= 1) {
            ItemStack slotStack = inv.getStackInSlot(0);

            if(!slotStack.isEmpty() && ingredient.test(slotStack)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(PulveroseInvWrapper inv) {
        return result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.PULVEROSE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.PULVEROSE;
    }

    public int getCost() {
        return cost;
    }
}
