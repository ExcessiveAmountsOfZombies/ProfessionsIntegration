package com.epherical.professionsi.integrations;

import com.epherical.croptopia.register.Content;
import com.epherical.professions.Constants;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.items.FishingAction;
import com.epherical.professions.profession.editor.Append;
import com.google.gson.Gson;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;

public class CroptopiaModule extends Module {

    public CroptopiaModule() {
        super("croptopia");
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
        Append.Builder append = Append.Builder.appender(id);
        append.addAction(Actions.FISH_ACTION, FishingAction.fish()
                .item(Content.ANCHOVY.asItem(), Content.CLAM.asItem(), Content.CRAB.asItem(), Content.OYSTER.asItem(),
                        Content.SHRIMP.asItem(), Content.TUNA.asItem())
                .reward(helper.moneyReward(26))
                .reward(helper.expReward(26)));
        helper.generate(cache, append.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendHunting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

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
