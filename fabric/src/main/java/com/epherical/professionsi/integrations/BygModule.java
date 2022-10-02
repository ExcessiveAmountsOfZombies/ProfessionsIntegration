package com.epherical.professionsi.integrations;

import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.blocks.BreakBlockAction;
import com.epherical.professions.profession.action.builtin.blocks.PlaceBlockAction;
import com.epherical.professions.profession.action.builtin.items.CraftingAction;
import com.epherical.professions.profession.editor.Append;
import com.epherical.professionsi.integrations.Module;
import com.google.gson.Gson;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import potionstudios.byg.common.block.BYGBlocks;
import potionstudios.byg.common.item.BYGItems;

import java.io.IOException;
import java.nio.file.Path;

public class BygModule extends Module {

    public BygModule() {
        super("byg");
    }

    @Override
    public void appendAlchemist(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        //helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendBuilder(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        appender.addAction(Actions.PLACE_BLOCK, PlaceBlockAction.place()
                .block(BYGBlocks.BORIC_LANTERN.get(), BYGBlocks.CRYPTIC_LANTERN.get(),
                        BYGBlocks.GLOWSTONE_LANTERN.get(), BYGBlocks.THERIUM_LANTERN.get())
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(1))
        );
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));

    }

    @Override
    public void appendCrafting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BYGItems.THERIUM_GLASS.get())
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(1))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BYGItems.BORIC_LANTERN.get(), BYGItems.CRYPTIC_LANTERN.get(),
                        BYGItems.GLOWSTONE_LANTERN.get(), BYGItems.THERIUM_LANTERN.get())
                .reward(helper.moneyReward(1.5))
                .reward(helper.expReward(1.5))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BYGItems.HYPOGEAL_IMPERIUM.get())
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(5)));
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));

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
    public void appendMining(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(BYGBlocks.AMETRINE_ORE.get(), BYGBlocks.BUDDING_AMETRINE_ORE.get(), BYGBlocks.ANTHRACITE_ORE.get())
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(2))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(BYGBlocks.PENDORITE_ORE.get())
                .reward(helper.moneyReward(7))
                .reward(helper.expReward(7))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(BYGBlocks.EMERALDITE_ORE.get(), BYGBlocks.CRYPTIC_REDSTONE_ORE.get())
                .reward(helper.expReward(4))
                .reward(helper.moneyReward(4))
        );
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));
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
