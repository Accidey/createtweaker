package com.xulai.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;

@SuppressWarnings({"unchecked", "rawtypes"})
@ZenRegister
@Document("mods/CreateTweaker/recipe/ProcessingRecipeBuilder")
@NativeTypeRegistration(value = ProcessingRecipeBuilder.class, zenCodeName = "mods.createtweaker.ProcessingRecipeBuilder")
public class ExpandProcessingRecipeBuilder {

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withItemIngredients(ProcessingRecipeBuilder internal, IIngredient... ingredients) {

        internal.withItemIngredients(Arrays.stream(ingredients)
                .map(IIngredient::asVanillaIngredient)
                .toArray(Ingredient[]::new));
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withSingleItemOutput(ProcessingRecipeBuilder internal, IItemStack output) {

        internal.withSingleItemOutput(output.getInternal());
        return internal;
    }

    @SafeVarargs
    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withItemOutputs(ProcessingRecipeBuilder internal, Percentaged<IItemStack>... outputs) {

        internal.withItemOutputs(Arrays.stream(outputs)
                .map(CreateTweakerHelper::mapPercentagedToProcessingOutput)
                .toArray(ProcessingOutput[]::new));
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withFluidIngredients(ProcessingRecipeBuilder internal, CTFluidIngredient... ingredients) {

        internal.withFluidIngredients(Arrays.stream(ingredients)
                .map(CreateTweakerHelper::mapFluidIngredients)
                .toArray(SizedFluidIngredient[]::new));
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder withFluidOutputs(ProcessingRecipeBuilder internal, IFluidStack... outputs) {

        internal.withFluidOutputs(Arrays.stream(outputs)
                .map(iFluidStack -> iFluidStack.<FluidStack>getInternal())
                .toArray(FluidStack[]::new));
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder duration(ProcessingRecipeBuilder internal, int ticks) {

        internal.duration(ticks);
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder averageProcessingDuration(ProcessingRecipeBuilder internal) {

        internal.averageProcessingDuration();
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder requiresHeat(ProcessingRecipeBuilder internal, HeatCondition condition) {

        internal.requiresHeat(condition);
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder require(ProcessingRecipeBuilder internal, IIngredient ingredient) {

        internal.require(ingredient.asVanillaIngredient());
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder require(ProcessingRecipeBuilder internal, CTFluidIngredient ingredient) {

        internal.require(CreateTweakerHelper.mapFluidIngredients(ingredient));
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder output(ProcessingRecipeBuilder internal, Percentaged<IItemStack> item) {

        internal.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(item));
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder output(ProcessingRecipeBuilder internal, IFluidStack fluidStack) {

        internal.output(fluidStack.<FluidStack>getInternal());
        return internal;
    }

    @ZenCodeType.Method
    public static ProcessingRecipeBuilder toolNotConsumed(ProcessingRecipeBuilder internal) {

        if(internal instanceof ItemApplicationRecipe.Builder<?> builder) {
            builder.toolNotConsumed();
            return internal;
        }
        throw new IllegalArgumentException("The recipe '%s' cannot keep its held item!".formatted(internal.getClass()
                .getName()));
    }

}
