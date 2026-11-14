package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.SandPaperPolishingManager")
@Document("mods/CreateTweaker/SandPaperPolishingManager")
public class SandPaperPolishingManager implements IProcessingRecipeManager<SandPaperPolishingRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack> output, IIngredient input, @ZenCodeType.OptionalInt(100) int duration) {

        ResourceLocation recipeId = fixRecipeId(name);
        StandardProcessingRecipe.Builder<SandPaperPolishingRecipe> builder = builder(recipeId);
        builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(output));
        builder.require(input.asVanillaIngredient());

        builder.duration(duration);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));

    }

    @Override
    public StandardProcessingRecipe.Builder<SandPaperPolishingRecipe> builder(ResourceLocation id) {

        return new StandardProcessingRecipe.Builder<>(SandPaperPolishingRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.SANDPAPER_POLISHING;
    }

}
