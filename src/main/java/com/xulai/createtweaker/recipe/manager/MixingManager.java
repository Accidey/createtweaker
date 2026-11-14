package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.create.MixingManager")
@Document("mods/CreateTweaker/MixingManager")
public class MixingManager implements IProcessingRecipeManager<MixingRecipe> {

    @Deprecated(forRemoval = true)
    public void addRecipe(String name, HeatCondition heat, Percentaged<IItemStack>[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.IFluidStack[]") IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {

        addRecipe(name, heat, outputs, itemInputs, Arrays.stream(fluidInputs)
                .map(IFluidStack::asFluidIngredient)
                .toArray(CTFluidIngredient[]::new), duration);
    }

    @Deprecated(forRemoval = true)
    public void addRecipe(String name, HeatCondition heat, IFluidStack[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.IFluidStack[]") IFluidStack[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {

        addRecipe(name, heat, outputs, itemInputs, Arrays.stream(fluidInputs)
                .map(IFluidStack::asFluidIngredient)
                .toArray(CTFluidIngredient[]::new), duration);
    }

    @ZenCodeType.Method
    public void addRecipe(String name, HeatCondition heat, Percentaged<IItemStack>[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.FluidIngredient[]") CTFluidIngredient[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<MixingRecipe> builder = builder(recipeId);
        for(Percentaged<IItemStack> output : outputs) {

            builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(output));
        }

        builder.withItemIngredients(CreateTweakerHelper.itemInputsToIngredients(itemInputs));
        if(fluidInputs != null && fluidInputs.length != 0) {
            builder.withFluidIngredients(Arrays.stream(fluidInputs)
                    .map(CreateTweakerHelper::mapFluidIngredients)
                    .toArray(SizedFluidIngredient[]::new));
        }
        builder.requiresHeat(heat);

        builder.duration(duration);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @ZenCodeType.Method
    public void addRecipe(String name, HeatCondition heat, IFluidStack[] outputs, IIngredientWithAmount[] itemInputs, @ZenCodeType.Optional("[] as crafttweaker.api.fluid.FluidIngredient[]") CTFluidIngredient[] fluidInputs, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<MixingRecipe> builder = builder(recipeId);

        builder.withFluidOutputs(Arrays.stream(outputs)
                .map(iFluidStack -> iFluidStack.<FluidStack>getInternal())
                .toArray(FluidStack[]::new));

        builder.withItemIngredients(CreateTweakerHelper.itemInputsToIngredients(itemInputs));
        if(fluidInputs != null && fluidInputs.length != 0) {
            builder.withFluidIngredients(Arrays.stream(fluidInputs)
                    .map(CreateTweakerHelper::mapFluidIngredients)
                    .toArray(SizedFluidIngredient[]::new));
        }
        builder.requiresHeat(heat);

        builder.duration(duration);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @Deprecated(forRemoval = true)
    public void remove(IFluidStack output) {

        remove(output.asFluidIngredient());
    }

    @ZenCodeType.Method
    public void remove(CTFluidIngredient output) {

        CraftTweakerAPI.apply(new ActionRemoveRecipe<>(this, holder -> {
            List<IFluidStack> fluidResults = CreateTweakerHelper.getRecipeFluidResults(holder.value());
            return fluidResults.stream().anyMatch(output::matches);
        }).describeDefaultRemoval(output));
    }

    @Override
    public StandardProcessingRecipe.Builder<MixingRecipe> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<>(MixingRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.MIXING;
    }

}
