package com.epherical.professionsi.integrations;

import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.blocks.BreakBlockAction;
import com.epherical.professions.profession.action.builtin.items.CraftingAction;
import com.epherical.professions.profession.conditions.builtin.ToolMatcher;
import com.epherical.professions.profession.editor.Append;
import com.google.gson.Gson;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import nourl.mythicmetals.MythicMetals;
import nourl.mythicmetals.tools.MythicTools;

import java.io.IOException;
import java.nio.file.Path;

import static nourl.mythicmetals.data.MythicTags.Blocks.*;

public class MythicMetalsModule extends Module {


    public MythicMetalsModule() {
        super(MythicMetals.MOD_ID);
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
    public void appendFarming(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendFishing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendHunting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(ADAMANTITE.oreBlock())
                .block(BANGLUM.oreBlock())
                .block(CARMOT.oreBlock())
                .block(KYBER.oreBlock())
                .block(ORICHALCUM.oreBlock())
                .block(PALLADIUM.oreBlock())
                .block(UNOBTAINIUM.oreBlock())
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(15))
                .condition(ToolMatcher.toolMatcher(ItemPredicate.Builder.item()
                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.ANY))).invert()));
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(AQUARIUM.oreBlock())
                .block(MANGANESE.oreBlock())
                .block(MORKITE.oreBlock())
                .block(MIDAS_GOLD.oreBlock())
                .block(MYTHRIL.oreBlock())
                .block(OSMIUM.oreBlock())
                .block(PLATINUM.oreBlock())
                .block(PROMETHEUM.oreBlock())
                .block(QUADRILLUM.oreBlock())
                .block(RUNITE.oreBlock())
                .block(SILVER.oreBlock())
                .block(STARRITE.oreBlock())
                .block(STORMYX.oreBlock())
                .reward(helper.moneyReward(4))
                .reward(helper.expReward(8))
                .condition(ToolMatcher.toolMatcher(ItemPredicate.Builder.item()
                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.ANY))).invert()));
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(TIN.oreBlock())
                .reward(helper.moneyReward(4))
                .reward(helper.expReward(4))
                .condition(ToolMatcher.toolMatcher(ItemPredicate.Builder.item()
                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.ANY))).invert()));
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendTrading(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendSmithing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);

        addSmithingCraftingData(appender, 3, 10, 6, helper, MythicTools.ADAMANTITE.getAxe(), MythicTools.ADAMANTITE.getPickaxe());
        addSmithingCraftingData(appender, 2, 10, 6, helper, MythicTools.ADAMANTITE.getHoe(), MythicTools.ADAMANTITE.getSword());
        addSmithingCraftingData(appender, 1, 10, 6, helper, MythicTools.ADAMANTITE.getShovel());

        addSmithingCraftingData(appender, 3, 10, 6, helper, MythicTools.BANGLUM.getAxe(), MythicTools.BANGLUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 10, 6, helper, MythicTools.BANGLUM.getHoe(), MythicTools.BANGLUM.getSword());
        addSmithingCraftingData(appender, 1, 10, 6, helper, MythicTools.BANGLUM.getShovel());

        addSmithingCraftingData(appender, 3, 10, 6, helper, MythicTools.CARMOT.getAxe(), MythicTools.CARMOT.getPickaxe());
        addSmithingCraftingData(appender, 2, 10, 6, helper, MythicTools.CARMOT.getHoe(), MythicTools.CARMOT.getSword());
        addSmithingCraftingData(appender, 1, 10, 6, helper, MythicTools.CARMOT.getShovel());

        addSmithingCraftingData(appender, 3, 10, 6, helper, MythicTools.KYBER.getAxe(), MythicTools.KYBER.getPickaxe());
        addSmithingCraftingData(appender, 2, 10, 6, helper, MythicTools.KYBER.getHoe(), MythicTools.KYBER.getSword());
        addSmithingCraftingData(appender, 1, 10, 6, helper, MythicTools.KYBER.getShovel());

        addSmithingCraftingData(appender, 3, 10, 6, helper, MythicTools.ORICHALCUM.getAxe(), MythicTools.ORICHALCUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 10, 6, helper, MythicTools.ORICHALCUM.getHoe(), MythicTools.ORICHALCUM.getSword());
        addSmithingCraftingData(appender, 1, 10, 6, helper, MythicTools.ORICHALCUM.getShovel());

        addSmithingCraftingData(appender, 3, 10, 6, helper, MythicTools.PALLADIUM.getAxe(), MythicTools.PALLADIUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 10, 6, helper, MythicTools.PALLADIUM.getHoe(), MythicTools.PALLADIUM.getSword());
        addSmithingCraftingData(appender, 1, 10, 6, helper, MythicTools.PALLADIUM.getShovel());

        addSmithingCraftingData(appender, 3, 6, 3, helper, MythicTools.AQUARIUM.getAxe(), MythicTools.AQUARIUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 6, 3, helper, MythicTools.AQUARIUM.getHoe(), MythicTools.AQUARIUM.getSword());
        addSmithingCraftingData(appender, 1, 6, 3, helper, MythicTools.AQUARIUM.getShovel());

        addSmithingCraftingData(appender, 3, 6, 3, helper, MythicTools.MYTHRIL.getAxe(), MythicTools.MYTHRIL.getPickaxe());
        addSmithingCraftingData(appender, 2, 6, 3, helper, MythicTools.MYTHRIL.getHoe(), MythicTools.MYTHRIL.getSword());
        addSmithingCraftingData(appender, 1, 6, 3, helper, MythicTools.MYTHRIL.getShovel());

        addSmithingCraftingData(appender, 3, 6, 3, helper, MythicTools.OSMIUM.getAxe(), MythicTools.OSMIUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 6, 3, helper, MythicTools.OSMIUM.getHoe(), MythicTools.OSMIUM.getSword());
        addSmithingCraftingData(appender, 1, 6, 3, helper, MythicTools.OSMIUM.getShovel());

        addSmithingCraftingData(appender, 3, 6, 3, helper, MythicTools.PROMETHEUM.getAxe(), MythicTools.PROMETHEUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 6, 3, helper, MythicTools.PROMETHEUM.getHoe(), MythicTools.PROMETHEUM.getSword());
        addSmithingCraftingData(appender, 1, 6, 3, helper, MythicTools.PROMETHEUM.getShovel());

        addSmithingCraftingData(appender, 3, 6, 3, helper, MythicTools.QUADRILLUM.getAxe(), MythicTools.QUADRILLUM.getPickaxe());
        addSmithingCraftingData(appender, 2, 6, 3, helper, MythicTools.QUADRILLUM.getHoe(), MythicTools.QUADRILLUM.getSword());
        addSmithingCraftingData(appender, 1, 6, 3, helper, MythicTools.QUADRILLUM.getShovel());

        addSmithingCraftingData(appender, 3, 6, 3, helper, MythicTools.RUNITE.getAxe(), MythicTools.RUNITE.getPickaxe());
        addSmithingCraftingData(appender, 2, 6, 3, helper, MythicTools.RUNITE.getHoe(), MythicTools.RUNITE.getSword());
        addSmithingCraftingData(appender, 1, 6, 3, helper, MythicTools.RUNITE.getShovel());
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendLogging(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }


    private void addSmithingCraftingData(Append.Builder appender, int itemAmount, double baseExp, double baseMoney, ProviderHelpers helpers, Item... items) {
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(items)
                .reward(helpers.moneyReward(baseMoney * itemAmount))
                .reward(helpers.expReward(baseExp * itemAmount))
        );
    }
}
