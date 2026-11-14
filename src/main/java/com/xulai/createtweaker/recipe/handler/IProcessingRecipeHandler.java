package com.xulai.createtweaker.recipe.handler;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.xulai.createtweaker.CreateTweakerHelper;
import com.xulai.createtweaker.recipe.replacement.CreateTweakerRecipeComponents;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;
import java.util.stream.Collectors;

public interface IProcessingRecipeHandler<T extends ProcessingRecipe<?, ?>> extends IRecipeHandler<T> {

    ResourceLocation RECIPE_ID = ResourceLocation.withDefaultNamespace("dummy");

    @Override
    default <U extends Recipe<?>> boolean doesConflict(final IRecipeManager<? super T> manager, final T first, final U secondRecipe) {

        if(isGoodRecipe(secondRecipe)) {
            final T second = (T) secondRecipe;
            if(first.getIngredients()
                    .size() != second.getIngredients()
                    .size() || !first.getRequiredHeat()
                    .equals(second.getRequiredHeat())) {
                return false;
            }
            return IngredientUtil.doIngredientsConflict(first.getIngredients(), second.getIngredients()) && CreateTweakerHelper
                    .doFluidIngredientsConflict(first, second);

        }

        return false;
    }

    @Override
    default Optional<IDecomposedRecipe> decompose(final IRecipeManager<? super T> manager, final RegistryAccess registryAccess, final T recipe) {

        return Optional.of(IDecomposedRecipe.builder()
                .with(BuiltinRecipeComponents.Input.INGREDIENTS, recipe.getIngredients()
                        .stream()
                        .map(IIngredient::fromIngredient)
                        .toList())
                .with(BuiltinRecipeComponents.Output.CHANCED_ITEMS, recipe.getRollableResults()
                        .stream()
                        .map(CreateTweakerHelper::mapProcessingResult)
                        .toList())
                .with(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS, recipe.getFluidIngredients()
                        .stream()
                        .map(CreateTweakerHelper::mapFluidIngredientsToCT)
                        .toList())
                .with(BuiltinRecipeComponents.Output.FLUIDS, CreateTweakerHelper.getRecipeFluidResults(recipe))
                .with(BuiltinRecipeComponents.Processing.TIME, recipe.getProcessingDuration())
                .with(CreateTweakerRecipeComponents.Input.HEAT, recipe.getRequiredHeat())
                .build());
    }

    @Override
    default Optional<T> recompose(final IRecipeManager<? super T> manager, final RegistryAccess registryAccess, final IDecomposedRecipe recipe) {

        ProcessingRecipeBuilder<?, T, ?> builder = builder(RECIPE_ID);
        builder.withItemIngredients(recipe.getOrThrow(BuiltinRecipeComponents.Input.INGREDIENTS)
                .stream()
                .map(IIngredient::asVanillaIngredient)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withItemOutputs(recipe.getOrThrow(BuiltinRecipeComponents.Output.CHANCED_ITEMS)
                .stream()
                .map(CreateTweakerHelper::mapPercentagedToProcessingOutput)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withFluidIngredients(recipe.getOrThrow(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS)
                .stream()
                .map(CreateTweakerHelper::mapFluidIngredients)
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.withFluidOutputs(recipe.getOrThrow(BuiltinRecipeComponents.Output.FLUIDS)
                .stream()
                .map(iFluidStack -> iFluidStack.<FluidStack>getInternal())
                .collect(Collectors.toCollection(NonNullList::create)));
        builder.duration(recipe.getOrThrowSingle(BuiltinRecipeComponents.Processing.TIME));
        builder.requiresHeat(recipe.getOrThrowSingle(CreateTweakerRecipeComponents.Input.HEAT));
        return Optional.of(builder.build());
    }

    boolean isGoodRecipe(Recipe<?> recipe);

    ProcessingRecipeBuilder<?, T, ?> builder(ResourceLocation id);

}
