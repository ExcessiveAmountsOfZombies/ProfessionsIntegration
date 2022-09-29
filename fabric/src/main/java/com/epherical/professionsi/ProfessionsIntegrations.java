package com.epherical.professionsi;

import com.epherical.professionsi.integrations.Module;
import com.epherical.professionsi.integrations.byg.BygModule;
import com.epherical.professionsi.integrations.byg.CommonModule;
import com.epherical.professionsi.integrations.byg.CroptopiaModule;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;

public class ProfessionsIntegrations implements ModInitializer {

    public static List<Module> moduleList = new ArrayList<>();


    @Override
    public void onInitialize() {
        moduleList.add(new BygModule());
        moduleList.add(new CommonModule());
        moduleList.add(new CroptopiaModule());


        for (Module module : moduleList) {
            module.loadDatapacks();
        }


    }
}
