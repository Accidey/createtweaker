package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@IRecipeHandler.For(EmptyingRecipe.class)
public class EmptyingRecipeHandler implements IProcessingRecipeHandler<EmptyingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super EmptyingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<EmptyingRecipe> holder) {

        EmptyingRecipe recipe = holder.value();
        List<IFluidStack> fluidResults = CreateTweakerHelper.getRecipeFluidResults(recipe);
        return String.format("<recipetype:create:emptying>.addRecipe(\"%s\", %s, %s, %s);",
                holder.id(),
                CreateTweakerHelper.mapProcessingResult(recipe.getRollableResults()
                                .get(0))
                        .getCommandString(),
                (fluidResults.isEmpty() ? IFluidStack.empty() : fluidResults.get(0)).getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients()
                                .get(0))
                        .getCommandString()
        );
    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof EmptyingRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, EmptyingRecipe, ?> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<EmptyingRecipe>(EmptyingRecipe::new, id);
    }

}
