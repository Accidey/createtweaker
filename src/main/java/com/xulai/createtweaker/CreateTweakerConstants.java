package com.xulai.createtweaker;

import net.minecraft.resources.ResourceLocation;

public class CreateTweakerConstants {

    public static final String MOD_ID = "createtweaker";
    public static final String MOD_NAME = "CreateTweaker";

    public static ResourceLocation rl(String path) {

        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
