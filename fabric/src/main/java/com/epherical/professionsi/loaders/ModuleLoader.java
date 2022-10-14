package com.epherical.professionsi.loaders;

import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professionsi.integrations.Module;
import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.data.CachedOutput;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

public class ModuleLoader {

    private final Supplier<Module> moduleSupplier;
    private final String modID;

    public ModuleLoader(String modID, Supplier<Module> module) {
        this.modID = modID;
        this.moduleSupplier = module;
    }

    protected boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded(modID);
    }


    public void loadDatapacks() {
        if (isModLoaded()) {
            moduleSupplier.get().loadDatapacks();
        }
    }

    public void generateData(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper) throws IOException {
        if (isModLoaded()) {
            moduleSupplier.get().generateData(cache, path, gson, helper);
        }
    }




}
