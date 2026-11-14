package com.xulai.createtweaker.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.google.gson.reflect.TypeToken;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.xulai.createtweaker.CreateTweakerConstants;

import java.util.Objects;

public final class CreateTweakerRecipeComponents {

    public static final class Input {

        public static final IRecipeComponent<HeatCondition> HEAT = IRecipeComponent.simple(
                CreateTweakerConstants.rl("input/heat"),
                new TypeToken<>() {},
                Objects::equals
        );

        private Input() {}

    }

}
