package com.epherical.professionsi.integrations.farmerdelight;

import com.epherical.professions.ProfessionsFabric;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.integration.cardinal.PlayerOwning;
import com.epherical.professions.profession.ProfessionContext;
import com.epherical.professions.profession.ProfessionParameter;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.blocks.BreakBlockAction;
import com.epherical.professions.profession.action.builtin.blocks.PlaceBlockAction;
import com.epherical.professions.profession.editor.Append;
import com.epherical.professions.trigger.RewardHandler;
import com.epherical.professionsi.integrations.Module;
import com.google.gson.Gson;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class FarmersDelightModule extends Module {

    public FarmersDelightModule() {
        super(FarmersDelightMod.MOD_ID);
    }


    public static void dontLoadMixin(Set<String> mixins) {
        if (!FabricLoader.getInstance().isModLoaded(FarmersDelightMod.MOD_ID)) {
            mixins.add("com.epherical.professionsi.mixin.farmerdelight.ProfessionCookingPotMixin");
            mixins.add("com.epherical.professionsi.mixin.farmerdelight.ProfessionSkilletMixin");
        }

    }

    @Override
    public void registerOwnableBlocks(BlockComponentFactoryRegistry registry, ComponentKey<PlayerOwning> key) {
        registry.registerFor(SkilletBlockEntity.class, key, PlayerOwning::new);
    }

    @Override
    public void registerProfessionHandlers() {
        DelightEvent.SKILLET_COOK_EVENT.register((owner, smeltedItem, recipe, blockEntity) -> {
            if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
                ServerLevel level = (ServerLevel) blockEntity.getLevel();
                ProfessionContext.Builder builder = new ProfessionContext.Builder(level)
                        .addRandom(level.random)
                        .addParameter(ProfessionParameter.THIS_PLAYER, ProfessionsFabric.getInstance().getPlayerManager().getPlayer(owner))
                        .addParameter(ProfessionParameter.ACTION_TYPE, Actions.ON_ITEM_COOK)
                        .addParameter(ProfessionParameter.ITEM_INVOLVED, smeltedItem)
                        .addParameter(ProfessionParameter.THIS_BLOCKSTATE, blockEntity.getBlockState())
                        .addParameter(ProfessionParameter.RECIPE_CRAFTED, recipe);
                RewardHandler.handleReward(builder);
            }
        });
    }

    @Override
    public void appendAlchemist(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendBuilder(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder app = Append.Builder.appender(id);
        app.addAction(Actions.PLACE_BLOCK, PlaceBlockAction.place()
                .block(BlocksRegistry.STOVE.get(), BlocksRegistry.COOKING_POT.get(), BlocksRegistry.SKILLET.get(),
                        BlocksRegistry.OAK_CABINET.get(), BlocksRegistry.SPRUCE_CABINET.get(), BlocksRegistry.CRIMSON_CABINET.get(),
                        BlocksRegistry.BIRCH_CABINET.get(), BlocksRegistry.JUNGLE_CABINET.get(), BlocksRegistry.ACACIA_CABINET.get(),
                        BlocksRegistry.DARK_OAK_CABINET.get(), BlocksRegistry.WARPED_CABINET.get())
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(1)));
        helper.generate(cache, app.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendCrafting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendEnchanting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFarming(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder app = Append.Builder.appender(id);
        app.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(TagsRegistry.WILD_CROPS)
                .reward(helper.moneyReward(0.5))
                .reward(helper.expReward(0.5)));
        helper.generate(cache, app.build(), createNormalPath(path, createAppendID(id.getPath()), false));

    }

    @Override
    public void appendFishing(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendHunting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

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
