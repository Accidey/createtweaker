package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

@IRecipeHandler.For(SandPaperPolishingRecipe.class)
public class SandPaperPolishingRecipeHandler implements IProcessingRecipeHandler<SandPaperPolishingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super SandPaperPolishingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<SandPaperPolishingRecipe> holder) {

        SandPaperPolishingRecipe recipe = holder.value();
        return String.format("<recipetype:create:sandpaper_polishing>.addRecipe(\"%s\", %s, %s);",
                holder.id(),
                CreateTweakerHelper.mapProcessingResult(recipe.getRollableResults()
                                .get(0))
                        .getCommandString(),
                IIngredient.fromIngredient(recipe.getIngredients()
                                .get(0))
                        .getCommandString()
        );
    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof SandPaperPolishingRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, SandPaperPolishingRecipe, ?> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<SandPaperPolishingRecipe>(SandPaperPolishingRecipe::new, id);
    }

}
