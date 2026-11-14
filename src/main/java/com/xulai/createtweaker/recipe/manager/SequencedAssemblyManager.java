package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("mods.create.SequencedAssemblyManager")
@Document("mods/CreateTweaker/SequencedAssemblyManager")
public class SequencedAssemblyManager implements IRecipeManager<SequencedAssemblyRecipe> {

    @ZenCodeType.Method
    public SequencedAssemblyRecipeBuilder builder(String name) {

        return new SequencedAssemblyRecipeBuilder(fixRecipeId(name));
    }

    @ZenCodeType.Method
    public void registerRecipe(String name, Consumer<SequencedAssemblyRecipeBuilder> recipeBuilder) {

        SequencedAssemblyRecipeBuilder builder = new SequencedAssemblyRecipeBuilder(fixRecipeId(name));
        recipeBuilder.accept(builder);
        addInternal(builder);
    }

    @ZenCodeType.Method
    public void addRecipe(SequencedAssemblyRecipeBuilder builder) {

        addInternal(builder);
    }

    private void addInternal(SequencedAssemblyRecipeBuilder builder) {

        RecipeHolder<SequencedAssemblyRecipe> holder = builder.build();
        precheck(holder.value());
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, holder));
    }

    private void precheck(SequencedAssemblyRecipe recipe) {

        if(recipe.getTransitionalItem()
                .isEmpty()) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `transitionTo` item is not provided or is air! transitionTo: " + recipe.getTransitionalItem());
        }
        try {
            if(AccessibleElementsProvider.get()
                    .registryAccess(recipe::getResultItem)
                    .isEmpty()) {
                throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `output` is not provided or is air! output: " + recipe.getTransitionalItem());
            }
        } catch(NoSuchElementException e) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `output` is not provided or is air! output: " + recipe.getTransitionalItem());
        }
        if(recipe.getIngredient() == null) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! The `inputs` is not provided! inputs: " + recipe.getIngredient());
        }
        if(recipe.getSequence()
                .isEmpty()) {
            throw new IllegalArgumentException("Error while adding Sequenced Assembly Recipe! No Steps have been provided!");
        }
    }

    @Override
    public RecipeType<SequencedAssemblyRecipe> getRecipeType() {

        return AllRecipeTypes.SEQUENCED_ASSEMBLY.getType();
    }

}
