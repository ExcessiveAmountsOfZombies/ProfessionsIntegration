package com.epherical.professionsi;

import com.epherical.professions.FabricRegConstants;
import com.epherical.professionsi.integrations.BygModule;
import com.epherical.professionsi.integrations.CommonModule;
import com.epherical.professionsi.integrations.CroptopiaFarmersModule;
import com.epherical.professionsi.integrations.CroptopiaModule;
import com.epherical.professionsi.integrations.MythicMetalsModule;
import com.epherical.professionsi.integrations.farmerdelight.FarmersDelightModule;
import com.epherical.professionsi.integrations.techreborn.TechRebornModule;
import com.epherical.professionsi.loaders.CommonLoader;
import com.epherical.professionsi.loaders.CompoundLoader;
import com.epherical.professionsi.loaders.ModuleLoader;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import net.fabricmc.api.ModInitializer;
import nourl.mythicmetals.MythicMetals;
import techreborn.TechReborn;

import java.util.ArrayList;
import java.util.List;

public class ProfessionsIntegrations implements ModInitializer {

    public static List<ModuleLoader> moduleList = new ArrayList<>();


    @Override
    public void onInitialize() {
        FabricRegConstants.init(); // ...
        moduleList.add(new ModuleLoader(BygModule.ID, () -> new BygModule(BygModule.ID)));
        moduleList.add(new CommonLoader("c", CommonModule::new));

        ModuleLoader croptopia = new ModuleLoader("croptopia", () -> new CroptopiaModule());
        ModuleLoader farmers = new ModuleLoader(FarmersDelightMod.MOD_ID, () -> new FarmersDelightModule());
        moduleList.add(croptopia);
        moduleList.add(farmers);
        moduleList.add(new CompoundLoader(() -> new CroptopiaFarmersModule(), croptopia, farmers));
        moduleList.add(new ModuleLoader(TechReborn.MOD_ID, () -> new TechRebornModule()));
        moduleList.add(new ModuleLoader(MythicMetals.MOD_ID, () -> new MythicMetalsModule()));


        for (ModuleLoader module : moduleList) {
            module.loadDatapacks();
        }
    }
}
