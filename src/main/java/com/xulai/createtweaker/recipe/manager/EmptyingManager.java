package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.action.recipe.ActionRemoveRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.create.EmptyingManager")
@Document("mods/CreateTweaker/EmptyingManager")
public class EmptyingManager implements IProcessingRecipeManager<EmptyingRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack> outputItem, IFluidStack outputFluid, IIngredient inputContainer, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<EmptyingRecipe> builder = builder(recipeId);
        builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(outputItem));
        builder.output(outputFluid.<FluidStack>getInternal());
        builder.require(inputContainer.asVanillaIngredient());

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
    public StandardProcessingRecipe.Builder<EmptyingRecipe> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<>(EmptyingRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.EMPTYING;
    }

}
