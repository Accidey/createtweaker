package com.xulai.createtweaker.recipe.type;

import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.xulai.createtweaker.recipe.fun.DeployerRecipeFunction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CTDeployerApplicationRecipe extends DeployerApplicationRecipe {

    private final DeployerRecipeFunction function;

    public CTDeployerApplicationRecipe(ItemApplicationRecipe.Builder<DeployerApplicationRecipe> builder, DeployerRecipeFunction function) {

        super(builder.build()
                .getParams());
        this.function = function;
    }

    @Override
    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults, RandomSource randomSource) {

        return super.rollResults(function.process(rollableResults), randomSource);
    }

}
