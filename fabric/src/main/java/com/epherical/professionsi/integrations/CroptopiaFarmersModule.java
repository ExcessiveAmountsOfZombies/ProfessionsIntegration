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
                .addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.COOKING_POT.asItem(), Content.FOOD_PRESS.asItem(), Content.FRYING_PAN.asItem())
                        .reward(helper.moneyReward(2))
                        .reward(helper.expReward(4)))
                .addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.OLIVE_OIL.asItem(), Content.CHEESE.asItem(), Content.FLOUR.asItem(), Content.BUTTER.asItem(),
                                Content.NOODLE.asItem(), Content.TOFU.asItem(), Content.TORTILLA.asItem(), Content.SOY_SAUCE.asItem(),
                                Content.DOUGH.asItem(), Content.RAVIOLI.asItem(), Content.ARTICHOKE_DIP.asItem(), Content.COFFEE.asItem(),
                                Content.LEMONADE.asItem(), Content.LIMEADE.asItem(), Content.SOY_MILK.asItem(), Content.DOUGHNUT.asItem(),
                                Content.OATMEAL.asItem(), Content.YOGHURT.asItem(), Content.SAUCY_CHIPS.asItem(), Content.SCRAMBLED_EGGS.asItem(),
                                Content.BUTTERED_TOAST.asItem(), Content.TOAST_WITH_JAM.asItem(), Content.CANDY_CORN.asItem(),
                                Content.SNICKER_DOODLE.asItem(), Content.CANDIED_NUTS.asItem(), Content.CREMA, Content.CHIMICHANGA, Content.CORN_HUSK,
                                Content.WHIPPING_CREAM, Content.TEA, Content.KIWI_SORBET, Content.PEANUT_BUTTER, Content.PEANUT_BUTTER_W_CELERY,
                                Content.STEAMED_BROCCOLI, Content.STEAMED_GREEN_BEANS)
                        .reward(helper.moneyReward(0.5))
                        .reward(helper.expReward(0.8)))
                .addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.CHOCOLATE.asItem(), Content.SALSA.asItem(), Content.PEPPERONI.asItem(), Content.KALE_CHIPS.asItem(),
                                Content.POTATO_CHIPS.asItem(), Content.STEAMED_RICE.asItem(), Content.FRENCH_FRIES.asItem(),
                                Content.SWEET_POTATO_FRIES.asItem(), Content.CUCUMBER_SALAD.asItem(), Content.LEAFY_SALAD.asItem(),
                                Content.LEEK_SOUP.asItem(), Content.GRILLED_CHEESE.asItem(), Content.ALMOND_BRITTLE.asItem(), Content.OATMEAL_COOKIE,
                                Content.NUTTY_COOKIE, Content.BURRITO, Content.AJVAR_TOAST, Content.AVOCADO_TOAST, Content.CHEESY_ASPARAGUS, Content.CORN_BREAD,
                                Items.BREAD, Content.CANDIED_KUMQUATS, Content.STEAMED_CRAB, Content.HASHED_BROWN)
                        .reward(helper.moneyReward(1))
                        .reward(helper.expReward(1)))
                .addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item())
                .addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
                        .item(Content.BAKED_BEANS.asItem(), Content.BAKED_SWEET_POTATO.asItem(), Content.BAKED_YAM.asItem(),
                                Content.CARAMEL.asItem(), Content.MOLASSES.asItem(), Content.POPCORN.asItem(), Content.RAISINS.asItem(),
                                Content.ROASTED_NUTS.asItem(), Content.REFRIED_BEANS)
                        .reward(helper.moneyReward(2))
                        .reward(helper.expReward(2))
                ).addAction(Actions.ON_ITEM_COOK, SmeltItemAction.smelt()
                        .item(Content.COOKED_ANCHOVY.asItem(), Content.COOKED_BACON.asItem(), Content.COOKED_CALAMARI.asItem(),
                                Content.COOKED_SHRIMP.asItem(), Content.COOKED_TUNA.asItem(), Items.COOKED_COD, Items.COOKED_SALMON,
                                Content.TOAST.asItem())
                        .reward(helper.moneyReward(4))
                        .reward(helper.expReward(4))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.HAMBURGER.asItem(), Content.SUSHI.asItem(), Content.EGG_ROLL.asItem(), Content.TOSTADA,
                                Content.POTATO_SOUP, Content.ROASTED_PUMPKIN_SEEDS, Content.ROASTED_SUNFLOWER_SEEDS, Content.MASHED_POTATOES)
                        .reward(helper.moneyReward(1.4))
                        .reward(helper.expReward(1.4))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(createCommonTag("juices", Registry.ITEM))
                        .item(Content.BEER.asItem(), Content.WINE.asItem(), Content.MEAD.asItem(), Content.RUM.asItem())
                        .item(ItemsRegistry.MELON_JUICE.get(), ItemsRegistry.MELON_POPSICLE.get(), Content.ONION_RINGS.asItem(),
                                Content.CAESAR_SALAD.asItem(), Content.PORK_AND_BEANS.asItem(), Content.TRAIL_MIX.asItem(),
                                Content.PROTEIN_BAR.asItem(), Content.NOUGAT.asItem(), Content.HAM_SANDWICH.asItem(), Content.PEANUT_BUTTER_AND_JAM.asItem(),
                                Content.CHEESEBURGER.asItem(), Content.PIZZA.asItem(), Content.CHICKEN_AND_DUMPLINGS.asItem(),
                                Content.TOFU_AND_DUMPLINGS.asItem(), Content.SPAGHETTI_SQUASH.asItem(), Content.CHICKEN_AND_RICE.asItem(),
                                Content.YAM_JAM.asItem(), Content.BANANA_NUT_BREAD.asItem(), Content.CARNITAS, Content.ENCHILADA, Content.CHURROS,
                                Content.QUESADILLA, Content.AJVAR, Content.BEEF_STEW, Content.BEEF_STIR_FRY, Content.BUTTERED_GREEN_BEANS,
                                Content.LEMON_COCONUT_BAR, Content.STIR_FRY, Content.TOAST_SANDWICH, Content.PUMPKIN_SOUP, Content.MERINGUE,
                                Content.DEEP_FRIED_SHRIMP, Content.TUNA_ROLL, Content.CRAB_LEGS, Content.STEAMED_CLAMS,
                                Content.GRILLED_OYSTERS, Content.ANCHOVY_PIZZA, Content.DAUPHINE_POTATOES, Content.MACARON)
                        .reward(helper.moneyReward(2))
                        .reward(helper.expReward(2))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(createCommonTag("jams", Registry.ITEM))
                        .reward(helper.moneyReward(2))
                        .reward(helper.expReward(2))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.KALE_SMOOTHIE.asItem(), Content.FRUIT_SMOOTHIE.asItem(), Content.CHOCOLATE_MILKSHAKE.asItem(),
                                Content.PUMPKIN_SPICE_LATTE.asItem(), Content.CHEESE_PIZZA.asItem(), Content.LEMON_CHICKEN.asItem(),
                                Content.FRIED_CHICKEN.asItem(), Content.CHICKEN_AND_NOODLES.asItem(), Content.TACO.asItem(),
                                Content.CASHEW_CHICKEN.asItem(), Content.BANANA_CREAM_PIE.asItem(), Content.RUM_RAISIN_ICE_CREAM.asItem(),
                                Content.BROWNIES.asItem(), Content.HORCHATA, Content.FAJITAS, Content.ETON_MESS, Content.ROASTED_ASPARAGUS,
                                Content.ROASTED_RADISHES, Content.ROASTED_SQUASH, Content.ROASTED_TURNIPS, Content.FRIED_CALAMARI, Content.BAKED_CREPES,
                                Content.CROQUE_MONSIEUR, Content.QUICHE)
                        .reward(helper.moneyReward(3.5))
                        .reward(helper.expReward(4))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.MANGO_ICE_CREAM.asItem(), Content.CHOCOLATE_ICE_CREAM.asItem(), Content.PECAN_ICE_CREAM.asItem(),
                                Content.STRAWBERRY_ICE_CREAM.asItem(), Content.VANILLA_ICE_CREAM.asItem(), Content.CHILI_RELLENO, Content.FISH_AND_CHIPS,
                                Content.EGGPLANT_PARMESAN, Content.GRILLED_EGGPLANT, Content.SWEET_CREPES, Content.CROQUE_MADAME)
                        .reward(helper.expReward(4))
                        .reward(helper.moneyReward(4))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Content.APPLE_PIE.asItem(), Content.CHERRY_PIE.asItem(), Content.PECAN_PIE.asItem(), Content.RHUBARB_PIE.asItem(),
                                ItemsRegistry.ROASTED_MUTTON_CHOPS.get(), ItemsRegistry.STEAK_AND_POTATOES.get(), ItemsRegistry.GRILLED_SALMON.get(),
                                Content.TAMALES, Content.STUFFED_POBLANOS, Content.CORNISH_PASTY, Content.SCONES, Content.FIGGY_PUDDING,
                                Content.TREACLE_TART, Content.FRUIT_CAKE, Content.RHUBARB_CRISP, Content.STUFFED_ARTICHOKE, Content.CABBAGE_ROLL,
                                Content.BORSCHT, Content.GOULASH, Content.BEETROOT_SALAD, Content.CINNAMON_ROLL)
                        .reward(helper.expReward(6))
                        .reward(helper.moneyReward(6))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.ROAST_CHICKEN_BLOCK.get(), ItemsRegistry.HONEY_GLAZED_HAM_BLOCK.get(), Content.SUPREME_PIZZA.asItem(),
                                Content.PINEAPPLE_PEPPERONI_PIZZA.asItem(), Content.TRES_LECHE_CAKE, Content.TRIFLE,
                                Content.STICKY_TOFFEE_PUDDING, Content.PUMPKIN_BARS)
                        .reward(helper.expReward(8))
                        .reward(helper.moneyReward(8))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.SHEPHERDS_PIE_BLOCK.get(), Content.SHEPHERDS_PIE, Content.BEEF_WELLINGTON, Content.RATATOUILLE,
                                Content.THE_BIG_BREAKFAST)
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
                                ItemsRegistry.STUFFED_POTATO.get(), Content.FRUIT_SALAD.asItem(), Content.VEGGIE_SALAD.asItem(), Content.NETHER_WART_STEW)
                        .reward(helper.moneyReward(2.3))
                        .reward(helper.expReward(2.3))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.BARBECUE_STICK.get(), ItemsRegistry.CHICKEN_SANDWICH.get(),
                                ItemsRegistry.BACON_SANDWICH.get(), Content.BLT.asItem())
                        .reward(helper.moneyReward(2.8))
                        .reward(helper.expReward(3))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.EGG_SANDWICH.get(), ItemsRegistry.BACON_AND_EGGS.get())
                        .reward(helper.moneyReward(1.5))
                        .reward(helper.expReward(1.5))
                ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(ItemsRegistry.HAMBURGER.get(), ItemsRegistry.MUTTON_WRAP.get(), ItemsRegistry.PASTA_WITH_MEATBALLS.get(),
                                ItemsRegistry.PASTA_WITH_MUTTON_CHOP.get(), ItemsRegistry.VEGETABLE_NOODLES.get(), Content.TOFUBURGER.asItem())
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
