package com.xulai.createtweaker.natives.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
@ZenRegister
@Document("mods/CreateTweaker/recipe/type/ProcessingRecipe")
@NativeTypeRegistration(value = ProcessingRecipe.class, zenCodeName = "mods.createtweaker.ProcessingRecipe")
public class ExpandProcessingRecipe {

    private static final RandomSource RANDOM = RandomSource.create();

    @ZenCodeType.Method
    @ZenCodeType.Getter("fluidIngredients")
    public static List<SizedFluidIngredient> getFluidIngredients(ProcessingRecipe internal) {

        return internal.getFluidIngredients();
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("rollableResults")
    public static List<ProcessingOutput> getRollableResults(ProcessingRecipe internal) {

        return internal.getRollableResults();
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("fluidResults")
    public static List<IFluidStack> getFluidResults(ProcessingRecipe internal) {

        return CreateTweakerHelper.getRecipeFluidResults(internal);
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("rollableResultsAsItemStacks")
    public static List<IItemStack> getRollableResultsAsItemStacks(ProcessingRecipe internal) {

        return internal.getRollableResultsAsItemStacks()
                .stream()
                .map(stack -> IItemStack.of((ItemStack) stack))
                .toList();
    }

    @ZenCodeType.Method
    public static List<IItemStack> rollResults(ProcessingRecipe internal) {

        return internal.rollResults(RANDOM)
                .stream()
                .map(stack -> IItemStack.of((ItemStack) stack))
                .toList();
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("processingDuration")
    public static int getProcessingDuration(ProcessingRecipe internal) {

        return internal.getProcessingDuration();
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredHeat")
    public static HeatCondition getRequiredHeat(ProcessingRecipe internal) {

        return internal.getRequiredHeat();
    }

}
