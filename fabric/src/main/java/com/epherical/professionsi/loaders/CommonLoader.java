package com.epherical.professionsi.loaders;

import com.epherical.professionsi.integrations.Module;

import java.util.function.Supplier;

public class CommonLoader extends ModuleLoader {

    public CommonLoader(String modID, Supplier<Module> module) {
        super(modID, module);
    }

    @Override
    protected boolean isModLoaded() {
        return true;
    }
}
