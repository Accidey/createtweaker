package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.stream.Collectors;

@IRecipeHandler.For(DeployerApplicationRecipe.class)
public class DeployerApplicationRecipeHandler implements IProcessingRecipeHandler<DeployerApplicationRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super DeployerApplicationRecipe> manager, RegistryAccess registryAccess, RecipeHolder<DeployerApplicationRecipe> holder) {

        DeployerApplicationRecipe recipe = holder.value();
        return String.format("<recipetype:create:deploying>.addRecipe(\"%s\", %s, %s, [%s]);",
                holder.id(),
                IIngredient.fromIngredient(recipe.getProcessedItem())
                        .getCommandString(),
                IIngredient.fromIngredient(recipe.getRequiredHeldItem())
                        .getCommandString(),
                recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", "))
        );

    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof DeployerApplicationRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, DeployerApplicationRecipe, ?> builder(ResourceLocation id) {

        return new ItemApplicationRecipe.Builder<DeployerApplicationRecipe>(DeployerApplicationRecipe::new, id);
    }

}
