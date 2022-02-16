package com.github.almostreliable.lazierae2.recipe.type;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public abstract class MachineRecipe implements IRecipe<IInventory> {

    private final ResourceLocation id;
    NonNullList<Ingredient> inputs = NonNullList.create();
    private int processTime;
    private int energyCost;
    private ItemStack output;

    MachineRecipe(
        ResourceLocation id
    ) {
        this.id = id;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public void setEnergyCost(int energyCost) {
        this.energyCost = energyCost;
    }

    public NonNullList<Ingredient> getInputs() {
        return inputs;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }
}
