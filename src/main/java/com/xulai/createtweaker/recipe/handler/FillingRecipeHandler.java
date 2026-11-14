package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

@IRecipeHandler.For(FillingRecipe.class)
public class FillingRecipeHandler implements IProcessingRecipeHandler<FillingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super FillingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<FillingRecipe> holder) {

        FillingRecipe recipe = holder.value();
        return String.format("<recipetype:create:filling>.addRecipe(\"%s\", %s, %s, %s);",
                holder.id(),
                CreateTweakerHelper.mapProcessingResult(recipe.getRollableResults()
                                .get(0))
                        .getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients()
                                .get(0))
                        .getCommandString(),
                (recipe.getFluidIngredients()
                        .isEmpty() ? IFluidStack.empty() : CreateTweakerHelper.getMatchingFluidStacks(recipe.getRequiredFluid())
                        .get(0)).getCommandString()
        );

    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof FillingRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, FillingRecipe, ?> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<FillingRecipe>(FillingRecipe::new, id);
    }

}
