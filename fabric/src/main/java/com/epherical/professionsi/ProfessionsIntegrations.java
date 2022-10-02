package com.epherical.professionsi;

import com.epherical.professionsi.integrations.CroptopiaFarmersModule;
import com.epherical.professionsi.integrations.farmerdelight.FarmersDelightModule;
import com.epherical.professionsi.integrations.Module;
import com.epherical.professionsi.integrations.BygModule;
import com.epherical.professionsi.integrations.CommonModule;
import com.epherical.professionsi.integrations.CroptopiaModule;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;

public class ProfessionsIntegrations implements ModInitializer {

    public static List<Module> moduleList = new ArrayList<>();


    @Override
    public void onInitialize() {
        moduleList.add(new BygModule());
        moduleList.add(new CommonModule());
        CroptopiaModule croptopiaModule = new CroptopiaModule();
        moduleList.add(croptopiaModule);
        FarmersDelightModule farmersDelightModule = new FarmersDelightModule();
        moduleList.add(farmersDelightModule);
        moduleList.add(new CroptopiaFarmersModule(croptopiaModule, farmersDelightModule));


        for (Module module : moduleList) {
            module.loadDatapacks();
        }


    }
}
