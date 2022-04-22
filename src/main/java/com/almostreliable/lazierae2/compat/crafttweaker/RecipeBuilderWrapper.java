package com.almostreliable.lazierae2.compat.crafttweaker;

import com.almostreliable.lazierae2.content.machine.MachineType;
import com.almostreliable.lazierae2.recipe.builder.MachineRecipeBuilder;
import com.almostreliable.lazierae2.recipe.type.MachineRecipe;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

import static com.almostreliable.lazierae2.core.Constants.MOD_ID;

@SuppressWarnings("unused")
@ZenRegister
@Name("mods." + MOD_ID + ".RecipeBuilderWrapper")
public class RecipeBuilderWrapper {

    private final IRecipeManager<? super MachineRecipe> manager;
    private final ResourceLocation id;
    private final MachineRecipeBuilder builder;

    RecipeBuilderWrapper(
        IRecipeManager<? super MachineRecipe> manager, MachineType type, ResourceLocation id, IItemStack output
    ) {
        this.manager = manager;
        this.id = id;
        builder = switch (type) {
            case AGGREGATOR -> MachineRecipeBuilder.aggregator(output.getInternal().getItem(), output.getAmount());
            case CENTRIFUGE -> MachineRecipeBuilder.centrifuge(output.getInternal().getItem(), output.getAmount());
            case ENERGIZER -> MachineRecipeBuilder.energizer(output.getInternal().getItem(), output.getAmount());
            case ETCHER -> MachineRecipeBuilder.etcher(output.getInternal().getItem(), output.getAmount());
        };
    }

    @Method
    public RecipeBuilderWrapper input(IIngredient input) {
        builder.input(input.asVanillaIngredient());
        return this;
    }

    @Method
    public RecipeBuilderWrapper processingTime(int ticks) {
        builder.processingTime(ticks);
        return this;
    }

    @Method
    public RecipeBuilderWrapper energyCost(int energy) {
        builder.energyCost(energy);
        return this;
    }

    @Method
    public void build() {
        var recipe = builder.build(id);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(manager, recipe, ""));
    }
}