package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.stream.Collectors;

@IRecipeHandler.For(ManualApplicationRecipe.class)
public class ItemApplicationRecipeHandler implements IProcessingRecipeHandler<ManualApplicationRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super ManualApplicationRecipe> manager, RegistryAccess registryAccess, RecipeHolder<ManualApplicationRecipe> holder) {

        ManualApplicationRecipe recipe = holder.value();
        return String.format("<recipetype:create:item_application>.addRecipe(\"%s\", [%s], %s, %s);",
                holder.id(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")),
                IIngredient.fromIngredient(recipe.getProcessedItem())
                        .getCommandString(),
                IIngredient.fromIngredient(recipe.getRequiredHeldItem())
                        .getCommandString()

        );

    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof ManualApplicationRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, ManualApplicationRecipe, ?> builder(ResourceLocation id) {

        return new ItemApplicationRecipe.Builder<ManualApplicationRecipe>(ManualApplicationRecipe::new, id);
    }

}
