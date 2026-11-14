package com.xulai.createtweaker;

import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.xulai.createtweaker.recipe.replacement.CreateTweakerRecipeComponents;

@CraftTweakerPlugin(CreateTweakerConstants.MOD_ID + ":main")
public class CreateTweakerCTPlugin implements ICraftTweakerPlugin {

    @Override
    public void registerRecipeComponents(final IRecipeComponentRegistrationHandler handler) {

        handler.registerRecipeComponent(CreateTweakerRecipeComponents.Input.HEAT);
    }

}
