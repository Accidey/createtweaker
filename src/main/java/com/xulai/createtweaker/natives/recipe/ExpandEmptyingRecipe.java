package com.xulai.createtweaker.natives.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/CreateTweaker/recipe/type/EmptyingRecipe")
@NativeTypeRegistration(value = EmptyingRecipe.class, zenCodeName = "mods.createtweaker.EmptyingRecipe")
public class ExpandEmptyingRecipe {

    @ZenCodeType.Method
    @ZenCodeType.Getter("resultingFluid")
    public static IFluidStack getResultingFluid(EmptyingRecipe internal) {

        return CreateTweakerHelper.getRecipeFluidResults(internal)
                .get(0);
    }

}
