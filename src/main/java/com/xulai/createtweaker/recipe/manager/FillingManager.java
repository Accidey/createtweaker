package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.FillingManager")
@Document("mods/CreateTweaker/FillingManager")
public class FillingManager implements IProcessingRecipeManager<FillingRecipe> {

    @Deprecated(forRemoval = true)
    public void addRecipe(String name, Percentaged<IItemStack> output, IIngredient inputContainer, IFluidStack inputFluid, @ZenCodeType.OptionalInt(100) int duration) {

        addRecipe(name, output, inputContainer, inputFluid.asFluidIngredient(), duration);
    }

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack> output, IIngredient inputContainer, CTFluidIngredient inputFluid, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<FillingRecipe> builder = builder(recipeId);
        builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(output));
        builder.require(inputContainer.asVanillaIngredient());
        builder.require(CreateTweakerHelper.mapFluidIngredients(inputFluid));

        builder.duration(duration);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @Override
    public StandardProcessingRecipe.Builder<FillingRecipe> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<>(FillingRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.FILLING;
    }

}
