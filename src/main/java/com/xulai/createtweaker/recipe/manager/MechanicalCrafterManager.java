package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.crafter.MechanicalCraftingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.MechanicalCrafterManager")
@Document("mods/CreateTweaker/MechanicalCrafterManager")
public class MechanicalCrafterManager implements IRecipeManager<MechanicalCraftingRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IItemStack output, IIngredient[][] ingredients) {

        addInternal(name, output, ingredients, false);
    }

    @ZenCodeType.Method
    public void addMirroredRecipe(String name, IItemStack output, IIngredient[][] ingredients) {

        addInternal(name, output, ingredients, true);
    }

    private void addInternal(String name, IItemStack output, IIngredient[][] ingredients, boolean mirrored) {

        if(ingredients.length == 0 || ingredients[0].length == 0) {
            throw new IllegalArgumentException("Create Mechanical Crafter recipe needs at least one ingredient!");
        }
        int width = ingredients[0].length;
        for(IIngredient[] value : ingredients) {
            if(value.length != width) {
                throw new IllegalArgumentException("Create Mechanical Crafter IIngredient array needs to have the same length for all entries (the arrays need to contain the exact same amount of ingredients, use <item:minecraft:air> to pad the arrays out!)");
            }
        }

        ResourceLocation recipeId = fixRecipeId(name);
        NonNullList<Ingredient> ingredientList = NonNullList.create();

        for(IIngredient[] iIngredients : ingredients) {
            for(IIngredient iIngredient : iIngredients) {
                ingredientList.add(iIngredient.asVanillaIngredient());
            }
        }

        NonNullList<Ingredient> list = NonNullList.of(Ingredient.EMPTY, ingredientList.toArray(new Ingredient[0]));
        MechanicalCraftingRecipe recipe = new MechanicalCraftingRecipe("", CraftingBookCategory.MISC, RecipeUtil.createPattern(list, width, ingredients.length), output.getInternal(), mirrored);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, recipe)));
    }

    @Override
    public RecipeType<MechanicalCraftingRecipe> getRecipeType() {

        return AllRecipeTypes.MECHANICAL_CRAFTING.getType();
    }

}
