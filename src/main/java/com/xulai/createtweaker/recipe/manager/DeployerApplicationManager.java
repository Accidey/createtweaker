package com.xulai.createtweaker.recipe.manager;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.xulai.createtweaker.recipe.fun.DeployerRecipeFunction;
import com.xulai.createtweaker.recipe.manager.base.IProcessingRecipeManager;
import com.xulai.createtweaker.recipe.type.CTDeployerApplicationRecipe;
import com.xulai.createtweaker.CreateTweakerHelper;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenCodeType.Name("mods.create.DeployerApplicationManager")
@Document("mods/CreateTweaker/DeployerApplicationManager")
public class DeployerApplicationManager implements IProcessingRecipeManager<DeployerApplicationRecipe> {

    @ZenCodeType.Method
    public void addRecipe(String name, IIngredient processedItem, IIngredient heldItem, Percentaged<IItemStack>[] outputs, @ZenCodeType.OptionalBoolean boolean keepHeldItem) {

        if(outputs.length > 2) {
            throw new IllegalArgumentException(String.format("Deployer recipe has more outputs (%s) than supported (2)!", outputs.length));
        }
        ResourceLocation recipeId = fixRecipeId(name);
        ItemApplicationRecipe.Builder<DeployerApplicationRecipe> builder = builder(recipeId);
        builder.require(processedItem.asVanillaIngredient());
        builder.require(heldItem.asVanillaIngredient());
        if(keepHeldItem) {
            builder.toolNotConsumed();
        }
        for(Percentaged<IItemStack> stack : outputs) {
            builder.output(CreateTweakerHelper.mapPercentagedToProcessingOutput(stack));
        }
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, builder.build())));
    }

    @ZenCodeType.Method
    public void registerRecipe(String name, Consumer<ProcessingRecipeBuilder> recipeBuilder, DeployerRecipeFunction function) {

        ResourceLocation recipeId = fixRecipeId(name);
        ItemApplicationRecipe.Builder<DeployerApplicationRecipe> builder = builder(recipeId);
        recipeBuilder.accept(builder);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(this, createHolder(recipeId, new CTDeployerApplicationRecipe(builder, function))));
    }

    @Override
    public ItemApplicationRecipe.Builder<DeployerApplicationRecipe> builder(ResourceLocation id) {

        return new ItemApplicationRecipe.Builder<DeployerApplicationRecipe>(DeployerApplicationRecipe::new, id);
    }

    @Override
    public AllRecipeTypes getCreateRecipeType() {

        return AllRecipeTypes.DEPLOYING;
    }

}
