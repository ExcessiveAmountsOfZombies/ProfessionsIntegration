package com.epherical.professionsi.integrations.techreborn;

import net.fabricmc.loader.api.FabricLoader;
import techreborn.TechReborn;

import java.util.Set;

public class TechRebornMixins {


    public static void dontLoadMixin(Set<String> mixins) {
        if (!FabricLoader.getInstance().isModLoaded(TechReborn.MOD_ID)) {
            mixins.add("com.epherical.professionsi.mixin.techreborn.ProfessionRecipeCrafterMixin");
        }
    }
}
