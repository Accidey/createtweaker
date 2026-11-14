package com.xulai.createtweaker.recipe.manager.base;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("mods.create.IProcessingRecipeManager")
@Document("mods/CreateTweaker/IProcessingRecipeManager")
public interface IProcessingRecipeManager<T extends ProcessingRecipe<?, ?>> extends IRecipeManager<T> {

    @ZenCodeType.Method
    default void registerRecipe(String name, Consumer<ProcessingRecipeBuilder> recipeBuilder) {

        ResourceLocation recipeId = fixRecipeId(name);
        ProcessingRecipeBuilder<?, T, ?> builder = builder(recipeId);
        recipeBuilder.accept(builder);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    AllRecipeTypes getCreateRecipeType();

    ProcessingRecipeBuilder<?, T, ?> builder(ResourceLocation id);

    @Override
    default RecipeType<T> getRecipeType() {

        return GenericUtil.uncheck(getCreateRecipeType()
                .getType());
    }

}
