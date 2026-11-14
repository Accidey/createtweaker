package com.xulai.createtweaker.natives.recipe;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("mods/CreateTweaker/recipe/type/ItemApplicationRecipe")
@NativeTypeRegistration(value = ItemApplicationRecipe.class, zenCodeName = "mods.createtweaker.ItemApplicationRecipe")
public class ExpandItemApplicationRecipe {

    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldKeepHeldItem")
    public static boolean shouldKeepHeldItem(ItemApplicationRecipe internal) {

        return internal.shouldKeepHeldItem();
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("requiredHeldItem")
    public static IIngredient getRequiredHeldItem(ItemApplicationRecipe internal) {

        return IIngredient.fromIngredient(internal.getRequiredHeldItem());
    }

    @ZenCodeType.Method
    @ZenCodeType.Getter("processedItem")
    public static IIngredient getProcessedItem(ItemApplicationRecipe internal) {

        return IIngredient.fromIngredient(internal.getProcessedItem());
    }

}
