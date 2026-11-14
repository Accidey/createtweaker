package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@ZenRegister
@ZenCodeType.Name("mods.create.CrushingManager")
@Document("mods/CreateTweaker/CrushingManager")
public class CrushingManager implements IProcessingRecipeManager<CrushingRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<CrushingRecipe> builder = builder(recipeId);
        builder.withItemOutputs(Arrays.stream(output)
                .map(CreateTweakerHelper::mapPercentagedToProcessingOutput)
                .toArray(ProcessingOutput[]::new));

        builder.require(input.asVanillaIngredient());

        builder.duration(duration);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @Override
    public StandardProcessingRecipe.Builder<CrushingRecipe> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<>(CrushingRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.CRUSHING;
    }

}
