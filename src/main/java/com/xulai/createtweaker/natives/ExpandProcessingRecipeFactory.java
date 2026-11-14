package com.xulai.createtweaker.natives;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;

@ZenRegister
@Document("mods/CreateTweaker/recipe/ProcessingRecipeFactory")
@NativeTypeRegistration(value = ProcessingRecipe.Factory.class, zenCodeName = "mods.createtweaker.ProcessingRecipeFactory")
public class ExpandProcessingRecipeFactory {

}
