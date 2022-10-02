package com.epherical.professionsi.integrations;

import com.epherical.croptopia.register.Content;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.ProfessionBuilder;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.items.CraftingAction;
import com.epherical.professions.profession.action.builtin.items.SmeltItemAction;
import com.epherical.professions.profession.action.builtin.items.TakeSmeltAction;
import com.epherical.professionsi.integrations.farmerdelight.FarmersDelightModule;
import com.google.gson.Gson;
import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.io.IOException;
import java.nio.file.Path;

public class CroptopiaFarmersModule extends CompoundModule {

    public CroptopiaFarmersModule(CroptopiaModule croptopiaModule, FarmersDelightModule farmersDelightModule) {
        super(croptopiaModule, farmersDelightModule);
    }

    @Override
    public void generateData(HashCache cache, Path path, Gson gson, ProviderHelpers helper) throws IOException {
        super.generateData(cache, path, gson, helper);
        ProfessionBuilder chef = ProfessionBuilder.profession(
                TextColor.parseColor("#ede324"),
                TextColor.parseColor("#FFFFFF"),
                new String[]{
                        "Earn money and experience",
                        "by cooking foods."},
                "Chef", 100);
        chef.addExperienceScaling(helper.defaultLevelParser())
                .incomeScaling(helper.defaultIncomeParser())
                .addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
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
                        .item(ItemsRegistry.MELON_JUICE.get(), ItemsRegistry.MELON_POPSICLE.get())
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
                        .item(Content.APPLE_PIE.asItem(), Content.CHERRY_PIE.asItem(), Content.PECAN_PIE.asItem(),
                                ItemsRegistry.ROASTED_MUTTON_CHOPS.get(), ItemsRegistry.STEAK_AND_POTATOES.get(), ItemsRegistry.GRILLED_SALMON.get())
                        .reward(helper.expReward(6))
                        .reward(helper.moneyReward(6))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.ROAST_CHICKEN_BLOCK.get(), ItemsRegistry.HONEY_GLAZED_HAM_BLOCK.get())
                        .reward(helper.expReward(8))
                        .reward(helper.moneyReward(8))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.SHEPHERDS_PIE_BLOCK.get())
                        .reward(helper.expReward(10))
                        .reward(helper.moneyReward(10))
                ).addAction(Actions.TAKE_COOKED_ITEM, TakeSmeltAction.takeSmelt()
                        .item(ItemsRegistry.STUFFED_PUMPKIN_BLOCK.get())
                        .reward(helper.expReward(6))
                        .reward(helper.moneyReward(6))
                ).addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
                        .item(ItemsRegistry.FRIED_EGG.get())
                        .reward(helper.expReward(2))
                        .reward(helper.moneyReward(3))
                ).addAction(Actions.TAKE_COOKED_ITEM, TakeSmeltAction.takeSmelt()
                        .item(ItemsRegistry.HOT_COCOA.get(), ItemsRegistry.APPLE_CIDER.get())
                        .reward(helper.moneyReward(1.1))
                        .reward(helper.expReward(1.2))
                ).addAction(Actions.TAKE_COOKED_ITEM, TakeSmeltAction.takeSmelt()
                        .item(ItemsRegistry.TOMATO_SAUCE.get(), ItemsRegistry.WHEAT_DOUGH.get(), ItemsRegistry.RAW_PASTA.get(),
                                ItemsRegistry.PIE_CRUST.get(), ItemsRegistry.SWEET_BERRY_COOKIE.get(), ItemsRegistry.HONEY_COOKIE.get(),
                                ItemsRegistry.CABBAGE_ROLLS.get(), ItemsRegistry.COOKED_RICE.get())
                        .reward(helper.moneyReward(0.5))
                        .reward(helper.expReward(0.5))
                ).addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
                        .item(ItemsRegistry.BEEF_PATTY.get(), ItemsRegistry.COOKED_CHICKEN_CUTS.get(),
                                ItemsRegistry.COOKED_BACON.get(), ItemsRegistry.COOKED_COD_SLICE.get(),
                                ItemsRegistry.COOKED_SALMON_SLICE.get(), ItemsRegistry.COOKED_MUTTON_CHOPS.get(),
                                ItemsRegistry.SMOKED_HAM.get())
                        .reward(helper.moneyReward(0.5))
                        .reward(helper.expReward(0.5))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.FRUIT_SALAD.get(), ItemsRegistry.MIXED_SALAD.get(), ItemsRegistry.NETHER_SALAD.get(),
                                ItemsRegistry.STUFFED_POTATO.get())
                        .reward(helper.moneyReward(2.3))
                        .reward(helper.expReward(2.3))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.BARBECUE_STICK.get(), ItemsRegistry.CHICKEN_SANDWICH.get(),
                                ItemsRegistry.BACON_SANDWICH.get())
                        .reward(helper.moneyReward(2.8))
                        .reward(helper.expReward(3))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.EGG_SANDWICH.get(), ItemsRegistry.BACON_AND_EGGS.get())
                        .reward(helper.moneyReward(1.5))
                        .reward(helper.expReward(1.5))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.HAMBURGER.get(), ItemsRegistry.MUTTON_WRAP.get(), ItemsRegistry.PASTA_WITH_MEATBALLS.get(),
                                ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get(), ItemsRegistry.VEGETABLE_NOODLES.get())
                        .reward(helper.moneyReward(3.2))
                        .reward(helper.expReward(3.2))
                ).addAction(Actions.TAKE_COOKED_ITEM, TakeSmeltAction.takeSmelt()
                        .item(ItemsRegistry.DUMPLINGS.get(), ItemsRegistry.BEEF_STEW.get(), ItemsRegistry.FISH_STEW.get(),
                                ItemsRegistry.FRIED_RICE.get(), ItemsRegistry.PUMPKIN_SOUP.get(), ItemsRegistry.RATATOUILLE.get())
                        .reward(helper.moneyReward(1.5))
                        .reward(helper.expReward(1.5))
                ).addAction(Actions.TAKE_COOKED_ITEM, TakeSmeltAction.takeSmelt()
                        .item(ItemsRegistry.CHICKEN_SOUP.get(), ItemsRegistry.VEGETABLE_SOUP.get(), ItemsRegistry.BAKED_COD_STEW.get(),
                                ItemsRegistry.NOODLE_SOUP.get(), ItemsRegistry.SQUID_INK_PASTA.get())
                        .reward(helper.moneyReward(2))
                        .reward(helper.expReward(2))
                );


        helper.generate(gson, cache, chef.build(), createNormalPath(path, new ResourceLocation("professions:chef"), false));
    }
}
