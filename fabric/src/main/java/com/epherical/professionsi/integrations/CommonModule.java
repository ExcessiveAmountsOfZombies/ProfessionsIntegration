package com.epherical.professionsi.integrations;

import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.blocks.PlaceBlockAction;
import com.epherical.professions.profession.editor.Append;
import com.google.gson.Gson;
import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;

public class CommonModule extends Module {
    public CommonModule() {
        super("c");
    }

    @Override
    public void appendAlchemist(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendBuilder(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        appender.addAction(Actions.PLACE_BLOCK, PlaceBlockAction.place()
                .block(createCommonTag("workbenches", Registry.BLOCK))
                .block(createCommonTag("bookshelves", Registry.BLOCK))
                .reward(helper.moneyReward(1.3))
                .reward(helper.expReward(1.3))
        ).addAction(Actions.PLACE_BLOCK, PlaceBlockAction.place()
                .block(createCommonTag("flowers", Registry.BLOCK))
                .reward(helper.moneyReward(0.5))
                .reward(helper.expReward(0.5)));
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));

    }

    @Override
    protected boolean isModLoaded() {
        return true;
    }

    @Override
    public void appendCrafting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendEnchanting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFarming(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFishing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendHunting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

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
