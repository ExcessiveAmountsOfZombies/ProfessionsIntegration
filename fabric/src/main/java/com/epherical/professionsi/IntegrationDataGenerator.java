package com.epherical.professionsi;

import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professionsi.integrations.Module;
import com.epherical.professionsi.loaders.ModuleLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;

import static com.epherical.professionsi.ProfessionsIntegrations.moduleList;

public class IntegrationDataGenerator implements DataGeneratorEntrypoint, DataProvider, ProviderHelpers {

    protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    private DataGenerator dataGenerator;

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(this);
        this.dataGenerator = fabricDataGenerator;
    }

    @Override
    public void run(HashCache cache) throws IOException {
        Path path = this.dataGenerator.getOutputFolder();
        for (ModuleLoader module : moduleList) {
            module.generateData(cache, path, GSON, this);
        }
    }

    @Override
    public String getName() {
        return "ProfessionsI";
    }
}
