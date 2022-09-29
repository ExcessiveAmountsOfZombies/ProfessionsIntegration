package com.epherical.professionsi.integrations.byg;

import com.epherical.croptopia.register.Content;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.items.CraftingAction;
import com.epherical.professions.profession.action.builtin.items.FishingAction;
import com.epherical.professions.profession.action.builtin.items.SmeltItemAction;
import com.epherical.professions.profession.editor.Append;
import com.epherical.professionsi.integrations.Module;
import com.google.gson.Gson;
import com.mojang.datafixers.kinds.App;
import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.io.IOException;
import java.nio.file.Path;

public class CroptopiaModule extends Module {

    public CroptopiaModule() {
        super("croptopia");
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
        Append.Builder append = Append.Builder.appender(id);
        append.addAction(Actions.FISH_ACTION, FishingAction.fish()
                .item(Content.ANCHOVY.asItem(), Content.CLAM.asItem(), Content.CRAB.asItem(), Content.OYSTER.asItem(),
                        Content.SHRIMP.asItem(), Content.TUNA.asItem())
                .reward(helper.moneyReward(26))
                .reward(helper.expReward(26))
        ).addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
                .item(Content.BAKED_BEANS.asItem(), Content.BAKED_SWEET_POTATO.asItem(), Content.BAKED_YAM.asItem(),
                        Content.CARAMEL.asItem(), Content.MOLASSES.asItem(), Content.POPCORN.asItem(), Content.RAISINS.asItem())
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(2))
        ).addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
                .item(Content.COOKED_ANCHOVY.asItem(), Content.COOKED_BACON.asItem(), Content.COOKED_CALAMARI.asItem(),
                        Content.COOKED_SHRIMP.asItem(), Content.COOKED_TUNA.asItem(), Items.COOKED_COD, Items.COOKED_SALMON,
                        Content.TOAST.asItem())
                .reward(helper.moneyReward(4))
                .reward(helper.expReward(4))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(createCommonTag("juices", Registry.ITEM))
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(2))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(createCommonTag("jams", Registry.ITEM))
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(2))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(Content.MANGO_ICE_CREAM.asItem(), Content.CHOCOLATE_ICE_CREAM.asItem(), Content.PECAN_ICE_CREAM.asItem(),
                        Content.STRAWBERRY_ICE_CREAM.asItem(), Content.VANILLA_ICE_CREAM.asItem())
                .reward(helper.expReward(4))
                .reward(helper.moneyReward(4))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(Content.APPLE_PIE.asItem(), Content.CHERRY_PIE.asItem(), Content.PECAN_PIE.asItem())
                .reward(helper.expReward(6))
                .reward(helper.moneyReward(6)));
        helper.generate(gson, cache, append.build(), createNormalPath(path, createAppendID(id.getPath()), false));
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
