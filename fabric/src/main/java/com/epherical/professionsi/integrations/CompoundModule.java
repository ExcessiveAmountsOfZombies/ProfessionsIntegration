package com.epherical.professionsi.integrations;

import com.epherical.professions.datagen.ProviderHelpers;
import com.google.gson.Gson;
import net.minecraft.data.HashCache;
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
    public void appendAlchemist(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendBuilder(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendCrafting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendEnchanting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFarming(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFishing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendHunting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendTrading(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendSmithing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendLogging(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }
}
