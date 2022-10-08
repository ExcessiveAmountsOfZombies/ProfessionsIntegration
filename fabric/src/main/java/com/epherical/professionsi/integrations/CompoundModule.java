package com.epherical.professionsi.integrations;

import com.epherical.professions.datagen.ProviderHelpers;
import com.google.gson.Gson;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;

/**
 * A compound module represents modules where ALL previous modules must be present for it to load.
 */
public abstract class CompoundModule extends Module {


    private Module[] modules;

    public CompoundModule(Module... modules) {
        super("");
        this.modules = modules;
    }

    @Override
    protected boolean isModLoaded() {
        for (Module module : modules) {
            if (!module.isModLoaded())  {
                return false;
            }
        }
        return true;
    }

    @Override
    public void appendAlchemist(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendBuilder(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendCrafting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendEnchanting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFarming(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFishing(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendHunting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendTrading(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendSmithing(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendLogging(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }
}
