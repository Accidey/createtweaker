package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.stream.Collectors;

@IRecipeHandler.For(PressingRecipe.class)
public class PressingRecipeHandler implements IProcessingRecipeHandler<PressingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super PressingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<PressingRecipe> holder) {

        PressingRecipe recipe = holder.value();
        return String.format("<recipetype:create:pressing>.addRecipe(\"%s\", [%s], %s);",
                holder.id(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getIngredients()
                                .get(0))
                        .getCommandString()
        );
    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof PressingRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, PressingRecipe, ?> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<PressingRecipe>(PressingRecipe::new, id);
    }

}
