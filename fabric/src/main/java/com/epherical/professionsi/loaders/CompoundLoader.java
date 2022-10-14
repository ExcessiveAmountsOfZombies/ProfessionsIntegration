package com.epherical.professionsi.loaders;

import com.epherical.professionsi.integrations.Module;

import java.util.function.Supplier;

public class CompoundLoader extends ModuleLoader {

    private ModuleLoader[] modules;

    public CompoundLoader(Supplier<Module> supplier, ModuleLoader... loaders) {
        super("", supplier);
        this.modules = loaders;
    }

    @Override
    protected boolean isModLoaded() {
        for (ModuleLoader module : modules) {
            if (!module.isModLoaded()) {
                return false;
            }
        }
        return true;
    }
}
