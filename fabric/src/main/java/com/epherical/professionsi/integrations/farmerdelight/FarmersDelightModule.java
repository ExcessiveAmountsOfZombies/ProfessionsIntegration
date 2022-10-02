package com.epherical.professionsi.integrations.farmerdelight;

import com.epherical.professions.ProfessionsFabric;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.integration.cardinal.PlayerOwning;
import com.epherical.professions.profession.ProfessionContext;
import com.epherical.professions.profession.ProfessionParameter;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.trigger.RewardHandler;
import com.epherical.professionsi.integrations.Module;
import com.google.gson.Gson;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.data.HashCache;
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

    }

    @Override
    public void appendHunting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

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
