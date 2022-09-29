package com.epherical.professionsi.integrations;

import com.epherical.professions.Constants;
import com.epherical.professions.config.ProfessionConfig;
import com.epherical.professions.datagen.ProviderHelpers;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.io.IOException;
import java.nio.file.Path;

public abstract class Module {
    private final String modID;

    public Module(String modID) {
        this.modID = modID;
    }

    protected ResourceLocation createAppendID(String profession) {
        return Constants.modID("append/" + modID + "/" + profession);
    }

    protected boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded(modID);
    }

    protected <T> TagKey<T> createCommonTag(String tag, Registry<T> registry) {
        return TagKey.create(registry.key(), new ResourceLocation("c", tag));
    }

    protected <T> TagKey<T> createModTag(String tag, Registry<T> registry) {
        return TagKey.create(registry.key(), new ResourceLocation(modID, tag));
    }

    public void loadDatapacks() {
        if (ProfessionConfig.useBuiltinDatapack && isModLoaded()) {
            ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("professionsi", "fabric/normal/" + modID),
                    FabricLoader.getInstance().getModContainer("professionsi").get(), ResourcePackActivationType.DEFAULT_ENABLED);
            if (ProfessionConfig.useHardcoreDatapack) {
                ResourceManagerHelper.registerBuiltinResourcePack(new ResourceLocation("professionsi", "fabric/hardcore/" + modID),
                        FabricLoader.getInstance().getModContainer("professionsi").get(), ResourcePackActivationType.DEFAULT_ENABLED);
            }
        }
    }

    public void generateData(HashCache cache, Path path, Gson gson, ProviderHelpers helper) throws IOException {
        appendAlchemist(cache, path, gson, helper, Constants.modID("alchemy"));
        appendBuilder(cache, path, gson, helper, Constants.modID("building"));
        appendCrafting(cache, path, gson, helper, Constants.modID("crafting"));
        appendEnchanting(cache, path, gson, helper, Constants.modID("enchanting"));
        appendFarming(cache, path, gson, helper, Constants.modID("farming"));
        appendFishing(cache, path, gson, helper, Constants.modID("fishing"));
        appendHunting(cache, path, gson, helper, Constants.modID("hunting"));
        appendMining(cache, path, gson, helper, Constants.modID("mining"));
        appendTrading(cache, path, gson, helper, Constants.modID("trading"));
        appendSmithing(cache, path, gson, helper, Constants.modID("smithing"));
        appendLogging(cache, path, gson, helper, Constants.modID("logging"));
    }

    public abstract void appendAlchemist(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException;

    public abstract void appendBuilder(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException;

    public abstract void appendCrafting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException;

    public abstract void appendEnchanting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id);

    public abstract void appendFarming(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id);

    public abstract void appendFishing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException;

    public abstract void appendHunting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id);

    public abstract void appendMining(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException;

    public abstract void appendTrading(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id);

    public abstract void appendSmithing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id);

    public abstract void appendLogging(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id);

    protected Path createNormalPath(Path path, ResourceLocation id, boolean forge) {
        String namespace = id.getNamespace();
        return forge ? path.resolve("resourcepacks/forge/normal/" + modID + "/data/" + namespace + "/professions/occupations/" + id.getPath() + ".json")
                : path.resolve("resourcepacks/fabric/normal/" + modID + "/data/" + namespace + "/professions/occupations/" + id.getPath() + ".json");
    }

    protected Path createHardcoreAppenders(Path path, ResourceLocation id, boolean forge) {
        String namespace = id.getNamespace();
        return forge ? path.resolve("resourcepacks/forge/hardcore/" + modID + "/data/" + namespace + "/professions/occupations/" + id.getPath() + ".json")
                : path.resolve("resourcepacks/fabric/hardcore/" + modID + "data/" + namespace + "/professions/occupations/" + id.getPath() + ".json");
    }

}
