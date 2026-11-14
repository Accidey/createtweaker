package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.stream.Collectors;

@IRecipeHandler.For(CrushingRecipe.class)
public class CrushingRecipeHandler implements IProcessingRecipeHandler<CrushingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super CrushingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<CrushingRecipe> holder) {

        CrushingRecipe recipe = holder.value();
        return String.format("<recipetype:create:crushing>.addRecipe(\"%s\", [%s], %s, %s);",
                holder.id(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getIngredients()
                                .get(0))
                        .getCommandString(),
                recipe.getProcessingDuration()
        );

    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof CrushingRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, CrushingRecipe, ?> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<CrushingRecipe>(CrushingRecipe::new, id);
    }

}
