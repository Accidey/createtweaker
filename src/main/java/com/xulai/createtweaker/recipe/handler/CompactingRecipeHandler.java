package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.mojang.datafixers.util.Either;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@IRecipeHandler.For(CompactingRecipe.class)
public class CompactingRecipeHandler implements IProcessingRecipeHandler<CompactingRecipe> {

    @Override
    public String dumpToCommandString(IRecipeManager<? super CompactingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<CompactingRecipe> holder) {

        CompactingRecipe recipe = holder.value();
        Either<Stream<Percentaged<IItemStack>>, IFluidStack> result;
        List<IFluidStack> fluidResults = CreateTweakerHelper.getRecipeFluidResults(recipe);
        if(!fluidResults.isEmpty()) {
            result = Either.right(fluidResults.get(0));
        } else {
            result = Either.left(recipe.getRollableResults()
                    .stream()
                    .map(CreateTweakerHelper::mapProcessingResult));
        }


        return String.format("<recipetype:create:compacting>.addRecipe(\"%s\", <constant:create:heat_condition:%s>, [%s], [%s], [%s]);",
                holder.id(),
                recipe.getRequiredHeat()
                        .name()
                        .toLowerCase(Locale.ENGLISH),
                result.map(results -> results.map(Percentaged::getCommandString)
                        .collect(Collectors.joining(", ")), IFluidStack::getCommandString),
                recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .map(IIngredient::getCommandString)
                        .collect(Collectors.joining(", ")),
                CreateTweakerHelper.getRecipeFluidResults(recipe)
                        .stream()
                        .map(IFluidStack::getCommandString)
                        .collect(Collectors.joining(", "))
        );

    }

    @Override
    public boolean isGoodRecipe(Recipe<?> recipe) {

        return recipe instanceof CompactingRecipe;
    }

    @Override
    public ProcessingRecipeBuilder<?, CompactingRecipe, ?> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<CompactingRecipe>(CompactingRecipe::new, id);
    }

}
