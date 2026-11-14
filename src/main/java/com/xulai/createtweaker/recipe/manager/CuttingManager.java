package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.CuttingManager")
@Document("mods/CreateTweaker/CuttingManager")
public class CuttingManager implements IProcessingRecipeManager<CuttingRecipe> {

    @Deprecated(forRemoval = true)
    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack> output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {

        addRecipe(name, new Percentaged[] {output}, input, duration);
    }

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<CuttingRecipe> builder = builder(recipeId);
        for(Percentaged<IItemStack> mcWeightedItemStack : outputs) {
            builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(mcWeightedItemStack));
        }
        builder.require(input.asVanillaIngredient());

        builder.duration(duration);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @Override
    public StandardProcessingRecipe.Builder<CuttingRecipe> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<>(CuttingRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.CUTTING;
    }

}
