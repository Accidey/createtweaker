package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("mods.create.ItemApplicationManager")
@Document("mods/CreateTweaker/ItemApplicationManager")
public class ItemApplicationManager implements IProcessingRecipeManager<ItemApplicationRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, Percentaged<IItemStack>[] outputs, IIngredient block, IIngredient heldItem, @ZenCodeType.OptionalBoolean boolean keepHeldItem) {

        ResourceLocation recipeId = fixRecipeId(name);
        ItemApplicationRecipe.Builder<ItemApplicationRecipe> builder = builder(recipeId);
        builder.require(block.asVanillaIngredient());
        builder.require(heldItem.asVanillaIngredient());
        if(keepHeldItem) {
            builder.toolNotConsumed();
        }
        for(Percentaged<IItemStack> mcWeightedItemStack : outputs) {
            builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(mcWeightedItemStack));
        }
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @Override
    public ItemApplicationRecipe.Builder<ItemApplicationRecipe> builder(ResourceLocation id) {

        return new ItemApplicationRecipe.Builder<ItemApplicationRecipe>(ManualApplicationRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.ITEM_APPLICATION;
    }

}
