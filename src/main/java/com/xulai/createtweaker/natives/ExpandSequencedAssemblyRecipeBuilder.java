package com.xulai.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.world.item.Item;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@Document("mods/CreateTweaker/recipe/SequencedAssemblyRecipeBuilder")
@NativeTypeRegistration(value = SequencedAssemblyRecipeBuilder.class, zenCodeName = "mods.createtweaker.SequencedAssemblyRecipeBuilder")
public class ExpandSequencedAssemblyRecipeBuilder {

    @ZenCodeType.Method
    public static <T extends ProcessingRecipe<?, ?>> SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder internal, Class<T> clazz, Function<ProcessingRecipeBuilder, ProcessingRecipeBuilder> builder) {

        try {
            return CreateTweakerHelper.addStep(internal, clazz, builder);
        } catch(NullPointerException e) {
            throw new RuntimeException("Error while adding step to Sequenced Assembler recipe! Make sure the transitionTo item is set before adding any steps!", e);
        }
    }

    @ZenCodeType.Method
    public static <T extends ProcessingRecipe<?, ?>> SequencedAssemblyRecipeBuilder addStep(SequencedAssemblyRecipeBuilder internal, Class<T> clazz) {

        try {
            return CreateTweakerHelper.addStep(internal, clazz);
        } catch(NullPointerException e) {
            throw new RuntimeException("Error while adding step to Sequenced Assembler recipe! Make sure the transitionTo item is set before adding any steps!", e);
        }
    }

    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder require(SequencedAssemblyRecipeBuilder internal, IIngredient ingredient) {

        return internal.require(ingredient.asVanillaIngredient());
    }

    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder transitionTo(SequencedAssemblyRecipeBuilder internal, Item item) {

        return internal.transitionTo(item);
    }

    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder addOutput(SequencedAssemblyRecipeBuilder internal, IItemStack output, float weight) {

        return internal.addOutput(output.getInternal(), weight);
    }

    @ZenCodeType.Method
    public static SequencedAssemblyRecipeBuilder loops(SequencedAssemblyRecipeBuilder internal, int loops) {

        return internal.loops(loops);
    }

}
