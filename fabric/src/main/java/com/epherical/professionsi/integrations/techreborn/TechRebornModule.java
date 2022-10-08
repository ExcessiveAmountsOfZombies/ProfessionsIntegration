package com.epherical.professionsi.integrations.techreborn;

import com.epherical.professions.Constants;
import com.epherical.professions.ProfessionsFabric;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.integration.cardinal.PlayerOwning;
import com.epherical.professions.profession.ProfessionContext;
import com.epherical.professions.profession.ProfessionParameter;
import com.epherical.professions.profession.action.ActionType;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.blocks.BreakBlockAction;
import com.epherical.professions.profession.action.builtin.items.CraftingAction;
import com.epherical.professions.profession.editor.Append;
import com.epherical.professions.trigger.RewardHandler;
import com.epherical.professionsi.integrations.Module;
import com.google.gson.Gson;
import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import reborncore.common.crafting.RebornRecipeType;
import techreborn.TechReborn;
import techreborn.blockentity.machine.GenericMachineBlockEntity;
import techreborn.init.ModFluids;
import techreborn.init.ModRecipes;
import techreborn.items.DynamicCellItem;

import java.io.IOException;
import java.nio.file.Path;

import static techreborn.init.TRContent.*;

public class TechRebornModule extends Module {

    public static final ProfessionParameter<RebornRecipeType<?>> REBORN_RECIPE = ProfessionParameter.create("reborn_recipe");
    public static final ActionType TR_ALLOY_SMELTER = Actions.register(Constants.modID("tr_alloy_smelting"), new TechRebornMachineAction.Serializer(), "Alloy Smelting");
    public static final ActionType TR_ASSEMBLING = Actions.register(Constants.modID("tr_assembling"), new TechRebornMachineAction.Serializer(), "Assemble");
    public static final ActionType TR_BLAST_FURNACE = Actions.register(Constants.modID("tr_blast_furnace"), new TechRebornMachineAction.Serializer(), "Blast Smelting");
    public static final ActionType TR_CENTRIFUGE = Actions.register(Constants.modID("tr_centrifuge"), new TechRebornMachineAction.Serializer(), "Centrifuge");
    public static final ActionType TR_CHEMICAL_REACTOR = Actions.register(Constants.modID("tr_chem_react"), new TechRebornMachineAction.Serializer(), "Chemical Reacting");
    public static final ActionType TR_COMPRESSOR = Actions.register(Constants.modID("tr_compress"), new TechRebornMachineAction.Serializer(), "Compress");
    public static final ActionType TR_DISTILL = Actions.register(Constants.modID("tr_distillation"), new TechRebornMachineAction.Serializer(), "Distill");
    public static final ActionType TR_EXTRACTOR = Actions.register(Constants.modID("tr_extract"), new TechRebornMachineAction.Serializer(), "Extract");
    public static final ActionType TR_FLUID_REPLICATION = Actions.register(Constants.modID("tr_fluid_replicate"), new TechRebornMachineAction.Serializer(), "Fluid Replication");
    public static final ActionType TR_IMPLOSION_COMPRESSOR = Actions.register(Constants.modID("tr_implosion_comp"), new TechRebornMachineAction.Serializer(), "Compress with Implosions");
    public static final ActionType TR_INDUSTRIAL_ELECTROLYZER = Actions.register(Constants.modID("tr_electrolyze"), new TechRebornMachineAction.Serializer(), "Electrolyze");
    public static final ActionType TR_INDUSTRIAL_GRINDER = Actions.register(Constants.modID("tr_grinder"), new TechRebornMachineAction.Serializer(), "Grind");
    public static final ActionType TR_INDUSTRIAL_SAWMILL = Actions.register(Constants.modID("tr_sawmill"), new TechRebornMachineAction.Serializer(), "Sawmill");
    public static final ActionType TR_SOLID_CANNING = Actions.register(Constants.modID("tr_solid_canning"), new TechRebornMachineAction.Serializer(), "Canning");
    public static final ActionType TR_VACUUM_FREEZER = Actions.register(Constants.modID("tr_vacuum"), new TechRebornMachineAction.Serializer(), "Vacuum");
    public static final ActionType TR_WIRE_MILL = Actions.register(Constants.modID("tr_wire_mill"), new TechRebornMachineAction.Serializer(), "Mill Wires");

    public TechRebornModule() {
        super(TechReborn.MOD_ID);
    }

    @Override
    public void registerProfessionHandlers() {
        super.registerProfessionHandlers();
        TechRebornEvent.REBORN_MACHINE_FINISH_EVENT.register((owner, items, blockEntity, rebornRecipeType, recipe) -> {
            if (blockEntity.getLevel() != null && !blockEntity.getLevel().isClientSide) {
                for (ItemStack item : items) {
                    ServerLevel level = (ServerLevel) blockEntity.getLevel();
                    ProfessionContext.Builder builder = new ProfessionContext.Builder(level)
                            .addRandom(level.random)
                            .addParameter(ProfessionParameter.THIS_PLAYER, ProfessionsFabric.getInstance().getPlayerManager().getPlayer(owner))
                            .addParameter(ProfessionParameter.ACTION_TYPE, TechRebornMachineAction.types.get(rebornRecipeType))
                            .addParameter(REBORN_RECIPE, rebornRecipeType)
                            .addParameter(ProfessionParameter.RECIPE_CRAFTED, recipe)
                            .addParameter(ProfessionParameter.ITEM_INVOLVED, item)
                            .addParameter(ProfessionParameter.THIS_BLOCKSTATE, blockEntity.getBlockState());
                    RewardHandler.handleReward(builder);
                }
            }
        });
    }

    @Override
    public void registerOwnableBlocks(BlockComponentFactoryRegistry registry, ComponentKey<PlayerOwning> key) {
        registry.registerFor(GenericMachineBlockEntity.class, key, PlayerOwning::new);
    }

    @Override
    public void appendAlchemist(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendBuilder(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendCrafting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder builder = Append.Builder.appender(id);
        builder.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MachineBlocks.BASIC.getFrame().asItem(), MachineBlocks.BASIC.getCasing().asItem(),
                        MachineBlocks.ADVANCED.getFrame().asItem(), MachineBlocks.ADVANCED.getCasing().asItem())
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(5))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MachineBlocks.INDUSTRIAL.getFrame().asItem(), MachineBlocks.INDUSTRIAL.getCasing().asItem())
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(25))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(SolarPanels.BASIC.asItem(), SolarPanels.ADVANCED.asItem(), SolarPanels.INDUSTRIAL.asItem())
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(10))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(SolarPanels.ULTIMATE.asItem(), SolarPanels.QUANTUM.asItem())
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(30))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(StorageUnit.CRUDE.asItem(), Parts.BASIC_DISPLAY.asItem(), Parts.DIGITAL_DISPLAY.asItem(), Parts.DATA_STORAGE_CORE.asItem(),
                        Parts.DIAMOND_SAW_BLADE.asItem(), Parts.DIAMOND_GRINDING_HEAD.asItem(), Parts.NEUTRON_REFLECTOR.asItem(), RED_CELL_BATTERY,
                        WRENCH, PAINTING_TOOL, OMNI_TOOL)
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(3))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(StorageUnit.BASIC.asItem(), TankUnit.BASIC.asItem(), Machine.IRON_FURNACE.asItem(), Machine.ALARM.asItem(),
                        Machine.LAMP_INCANDESCENT.asItem(), Machine.LAMP_LED.asItem(), Parts.TUNGSTEN_GRINDING_HEAD.asItem(),
                        Parts.THICK_NEUTRON_REFLECTOR.asItem(), Upgrades.OVERCLOCKER.asItem(), Upgrades.TRANSFORMER.asItem(),
                        Upgrades.ENERGY_STORAGE.asItem(), ELECTRIC_TREE_TAP, ROCK_CUTTER)
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(6))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(StorageUnit.ADVANCED.asItem(), TankUnit.ADVANCED.asItem(), Machine.ALLOY_SMELTER.asItem(), Machine.COMPRESSOR.asItem(),
                        Machine.EXTRACTOR.asItem(), Machine.GRINDER.asItem(), Machine.ELECTRIC_FURNACE.asItem(), Machine.IRON_ALLOY_FURNACE.asItem(),
                        Machine.RECYCLER.asItem(), Machine.SCRAPBOXINATOR.asItem(), Machine.BLOCK_BREAKER.asItem(), Machine.BLOCK_PLACER.asItem(),
                        Machine.SOLID_FUEL_GENERATOR.asItem(), Machine.WATER_MILL.asItem(), Machine.DRAIN.asItem(), Machine.LAPOTRONIC_SU.asItem(),
                        Machine.LSU_STORAGE.asItem(), Machine.LOW_VOLTAGE_SU.asItem(), Machine.MEDIUM_VOLTAGE_SU.asItem(),
                        Machine.LV_TRANSFORMER.asItem(), Machine.MV_TRANSFORMER.asItem(), Machine.HV_TRANSFORMER.asItem(),
                        Parts.ENERGY_FLOW_CHIP.asItem(), Parts.IRIDIUM_NEUTRON_REFLECTOR.asItem(), LITHIUM_ION_BATPACK,
                        LAPOTRON_CRYSTAL, LAPOTRONIC_ORB, BASIC_DRILL, BASIC_CHAINSAW, BASIC_JACKHAMMER, ADVANCED_CHAINSAW, ADVANCED_DRILL,
                        ADVANCED_JACKHAMMER, NANOSABER)
                .reward(helper.moneyReward(4))
                .reward(helper.expReward(10))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(StorageUnit.INDUSTRIAL.asItem(), StorageUnit.QUANTUM.asItem(), TankUnit.INDUSTRIAL.asItem(), TankUnit.QUANTUM.asItem(),
                        Cables.SUPERCONDUCTOR.asItem(), Machine.AUTO_CRAFTING_TABLE.asItem(), Machine.CHEMICAL_REACTOR.asItem(), Machine.IMPLOSION_COMPRESSOR.asItem(),
                        Machine.ROLLING_MACHINE.asItem(), Machine.VACUUM_FREEZER.asItem(), Machine.SOLID_CANNING_MACHINE.asItem(),
                        Machine.WIRE_MILL.asItem(), Machine.GREENHOUSE_CONTROLLER.asItem(), Machine.DIESEL_GENERATOR.asItem(), Machine.WIND_MILL.asItem(),
                        Machine.SEMI_FLUID_GENERATOR.asItem(), Machine.THERMAL_GENERATOR.asItem(), Machine.CHUNK_LOADER.asItem(),
                        Machine.PLAYER_DETECTOR.asItem(), COMPUTER_CUBE.asItem(), Parts.DATA_STORAGE_CHIP.asItem(),
                        Upgrades.SUPERCONDUCTOR.asItem(), INDUSTRIAL_CHAINSAW, INDUSTRIAL_DRILL, INDUSTRIAL_JACKHAMMER, CLOAKING_DEVICE)
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(30))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(Cables.GLASSFIBER.asItem(), Cables.INSULATED_COPPER.asItem(), Cables.INSULATED_GOLD.asItem(),
                        Cables.INSULATED_HV.asItem(), REINFORCED_GLASS.asItem())
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(3))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(Machine.ASSEMBLY_MACHINE.asItem(), Machine.DISTILLATION_TOWER.asItem(), Machine.INDUSTRIAL_BLAST_FURNACE.asItem(),
                        Machine.INDUSTRIAL_CENTRIFUGE.asItem(), Machine.INDUSTRIAL_ELECTROLYZER.asItem(), Machine.INDUSTRIAL_SAWMILL.asItem(),
                        Machine.HIGH_VOLTAGE_SU.asItem(), NUKE.asItem(), LAPOTRONIC_ORBPACK)
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(50))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(Machine.INDUSTRIAL_GRINDER.asItem(), Machine.GAS_TURBINE.asItem(), Machine.CHARGE_O_MAT.asItem())
                .reward(helper.moneyReward(15))
                .reward(helper.expReward(100))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(Machine.FLUID_REPLICATOR.asItem(), Machine.MATTER_FABRICATOR.asItem(), Machine.DRAGON_EGG_SYPHON.asItem(),
                        Machine.FUSION_CONTROL_COMPUTER.asItem(), Machine.FUSION_COIL.asItem(), Machine.LIGHTNING_ROD.asItem(),
                        Machine.PLASMA_GENERATOR.asItem(), Machine.ADJUSTABLE_SU.asItem(), Machine.INTERDIMENSIONAL_SU.asItem(),
                        QUANTUM_HELMET, QUANTUM_CHESTPLATE, QUANTUM_LEGGINGS, QUANTUM_BOOTS)
                .reward(helper.moneyReward(20))
                .reward(helper.expReward(200))
        );

        builder.addAction(TR_ASSEMBLING, rebornAction(ModRecipes.ASSEMBLING_MACHINE, Parts.ELECTRONIC_CIRCUIT.asItem(), 0.5, 1, helper)
                .item(Parts.ADVANCED_CIRCUIT.asItem(), Parts.INDUSTRIAL_CIRCUIT.asItem(),
                        LITHIUM_ION_BATTERY, ENERGY_CRYSTAL));
        builder.addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.MERCURY, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.METHANE, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.OIL, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.CARBON, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.SILICON, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.SULFUR, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.DEUTERIUM, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.HELIUM3, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.HELIUM, helper))
                .addAction(TR_CENTRIFUGE, centrifugeCellAction(0.3, 0.25, ModFluids.TRITIUM, helper))
                .addAction(TR_CENTRIFUGE, rebornAction(ModRecipes.CENTRIFUGE, Dusts.ANDRADITE.asItem(), 0.1, 0.12, helper)
                        .item(Dusts.GROSSULAR.asItem())
                        .item(Dusts.UVAROVITE.asItem())
                        .item(Dusts.ENDER_PEARL.asItem())
                        .item(Dusts.CALCITE.asItem())
                        .item(Dusts.ASHES.asItem())
                        .item(Dusts.DARK_ASHES.asItem())
                        .item(Dusts.SULFUR.asItem())
                        .item(Dusts.SALTPETER.asItem())
                        .item(Dusts.COAL.asItem())
                        .item(Dusts.PYRITE.asItem())
                        .item(Dusts.RUBY.asItem())
                        .item(Dusts.PYROPE.asItem())
                        .item(Dusts.ALMANDINE.asItem())
                        .item(Dusts.SPESSARTINE.asItem())
                        .item(Dusts.MAGNESIUM.asItem())
                        .item(Dusts.LAZURITE.asItem())
                        .item(Dusts.PERIDOT.asItem())
                        .item(Dusts.FLINT.asItem())
                        .item(SmallDusts.TUNGSTEN.asItem())
                        .item(SmallDusts.PYRITE.asItem())
                        .item(SmallDusts.CALCITE.asItem())
                        .item(SmallDusts.SODALITE.asItem())
                        .item(Parts.RUBBER.asItem())
                        .item(Parts.SAP.asItem()).item())
                .addAction(TR_CENTRIFUGE, rebornAction(ModRecipes.CENTRIFUGE, Parts.PLANTBALL.asItem(), 0.15, 0.12, helper))
                .addAction(TR_CENTRIFUGE, rebornAction(ModRecipes.CENTRIFUGE, Nuggets.ZINC.asItem(), 0.1, 0.12, helper)
                        .item(Nuggets.LEAD.asItem())
                        .item(Nuggets.SILVER.asItem())
                        .item(Nuggets.NICKEL.asItem())
                        .item(Nuggets.TIN.asItem())
                        .item(Nuggets.COPPER.asItem()))
                .addAction(TR_CENTRIFUGE, rebornAction(ModRecipes.CENTRIFUGE, Nuggets.IRIDIUM.asItem(), 0.3, 0.4, helper))
                .addAction(TR_CENTRIFUGE, rebornAction(ModRecipes.CENTRIFUGE, Nuggets.IRIDIUM.asItem(), 0.3, 0.4, helper))
                .addAction(TR_CENTRIFUGE, rebornAction(ModRecipes.CENTRIFUGE, Items.DIRT, 0.1, 0.12, helper)
                        .item(Items.GLOWSTONE_DUST)
                        .item(Items.SAND)
                        .item(Items.RED_DYE)
                        .item(Items.DEAD_TUBE_CORAL)
                        .item(Items.DEAD_TUBE_CORAL_FAN)
                        .item(Items.NETHERRACK)
                        .item(Items.WARPED_FUNGUS)
                        .item(Items.CLAY_BALL)
                        .item(Items.GRAVEL)
                        .item(Items.HANGING_ROOTS)
                        .item(Items.IRON_NUGGET)
                        .item(Items.REDSTONE))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.CALCIUM, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.SILICON, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.COMPRESSED_AIR, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.HYDROGEN, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.SULFUR, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.POTASSIUM, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.NITROGEN, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.BERYLLIUM, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.ELECTROLYZED_WATER, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.CARBON, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.SODIUM, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.CHLORITE, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.WOLFRAMIUM, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.MERCURY, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, electrolyzerCellAction(0.25, 0.35, ModFluids.CARBON_FIBER, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, rebornAction(ModRecipes.INDUSTRIAL_ELECTROLYZER, Dusts.CHROME.asItem(), 0.25, 0.2, helper)
                        .item(Dusts.ALUMINUM.asItem(), Dusts.MANGANESE.asItem(), Dusts.MAGNESIUM.asItem(), Dusts.ZINC.asItem(), Dusts.SULFUR.asItem(), Items.RAW_IRON))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, rebornAction(ModRecipes.INDUSTRIAL_ELECTROLYZER, Items.RAW_IRON, 0.25, 0.2, helper))
                .addAction(TR_INDUSTRIAL_ELECTROLYZER, rebornAction(ModRecipes.INDUSTRIAL_ELECTROLYZER, SmallDusts.MAGNESIUM.asItem(), 0.1, 0.1, helper))

                .addAction(TR_DISTILL, cellBasedAction(ModRecipes.DISTILLATION_TOWER, 0.5, 0.5, ModFluids.DIESEL, helper))
                .addAction(TR_DISTILL, cellBasedAction(ModRecipes.DISTILLATION_TOWER, 0.5, 0.5, ModFluids.SULFURIC_ACID, helper))
                .addAction(TR_DISTILL, cellBasedAction(ModRecipes.DISTILLATION_TOWER, 0.5, 0.5, ModFluids.GLYCERYL, helper))

                .addAction(TR_EXTRACTOR, rebornAction(ModRecipes.EXTRACTOR, Items.YELLOW_DYE, 0.2, 0.2, helper)
                        .item(Items.WHITE_DYE, Items.RED_DYE, Items.PURPLE_DYE, Items.PINK_DYE, Items.ORANGE_DYE,
                                Items.MAGENTA_DYE, Items.LIGHT_GRAY_DYE, Items.LIGHT_BLUE_DYE, Items.GREEN_DYE,
                                Items.CYAN_DYE, Items.BROWN_DYE, Items.BLUE_DYE, Items.BLACK_DYE,
                                Items.GRAY_DYE, Items.GLASS_BOTTLE, Items.FLINT, Items.WHEAT_SEEDS, Items.SUGAR, Items.STRING,
                                Items.STICK, Items.PAPER))
                .addAction(TR_EXTRACTOR, rebornAction(ModRecipes.EXTRACTOR, Items.PHANTOM_MEMBRANE, 1, 1, helper))
                .addAction(TR_EXTRACTOR, rebornAction(ModRecipes.EXTRACTOR, Parts.SPONGE_PIECE.asItem(), 1, 1, helper))
                .addAction(TR_EXTRACTOR, rebornAction(ModRecipes.EXTRACTOR, Parts.RUBBER.asItem(), 0.2, 0.2, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, RawMetals.TUNGSTEN.asItem(), 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, RawMetals.TIN.asItem(), 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, RawMetals.SILVER.asItem(), 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, RawMetals.LEAD.asItem(), 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, RawMetals.IRIDIUM.asItem(), 1, 3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.RAW_IRON, 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.RAW_GOLD, 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.RAW_COPPER, 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.NETHERITE_SCRAP, 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.SPHALERITE.asItem(), 0.5, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.PLATINUM.asItem(), 0.5, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.SODALITE.asItem(), 0.5, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.ALUMINUM.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.ZINC.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.YELLOW_GARNET.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.NICKEL.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.PYRITE.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.SULFUR.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.OBSIDIAN.asItem(), 0.2, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.LAZURITE.asItem(), 0.2, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.GALENA.asItem(), 0.2, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.DIAMOND.asItem(), 0.2, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.TITANIUM.asItem(), 0.2, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.NETHERRACK.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.ENDSTONE.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.NETHERRACK.asItem(), 0.5, 0.5, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.SAPPHIRE.asItem(), 0.3, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.RUBY.asItem(), 0.3, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Dusts.PERIDOT.asItem(), 0.3, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Nuggets.IRIDIUM.asItem(), 2, 1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Nuggets.TIN.asItem(), 0.3, 0.4, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Nuggets.NICKEL.asItem(), 0.3, 0.4, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Nuggets.COPPER.asItem(), 0.3, 0.4, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Nuggets.NICKEL.asItem(), 0.3, 0.4, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.GOLD_NUGGET, 0.1, 0.2, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.GLOWSTONE.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.SULFUR.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.GALENA.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.PLATINUM.asItem(), 0.1, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.DIAMOND.asItem(), 0.1, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.TITANIUM.asItem(), 0.1, 0.3, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.SAPPHIRE.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.RUBY.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, SmallDusts.PERIDOT.asItem(), 0.1, 0.1, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Gems.SAPPHIRE.asItem(), 0.3, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Gems.RUBY.asItem(), 0.3, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Gems.PERIDOT.asItem(), 0.3, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.REDSTONE, 0.3, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.QUARTZ, 0.3, 0.35, helper))
                .addAction(TR_INDUSTRIAL_GRINDER, rebornAction(ModRecipes.INDUSTRIAL_GRINDER, Items.LAPIS_LAZULI, 0.3, 0.35, helper))

                .addAction(TR_INDUSTRIAL_SAWMILL, rebornAction(ModRecipes.INDUSTRIAL_SAWMILL, Dusts.SAW.asItem(), 0.2, 0.2, helper))

                .addAction(TR_SOLID_CANNING, rebornAction(ModRecipes.SOLID_CANNING_MACHINE, Parts.WATER_COOLANT_CELL_10K.asItem(), 0.2, 0.4, helper))
                .addAction(TR_SOLID_CANNING, rebornAction(ModRecipes.SOLID_CANNING_MACHINE, Parts.HELIUM_COOLANT_CELL_60K.asItem(), 0.2, 0.4, helper))
                .addAction(TR_SOLID_CANNING, cellBasedAction(ModRecipes.SOLID_CANNING_MACHINE, 0.2, 0.4, ModFluids.BIOFUEL, helper))
                .addAction(TR_SOLID_CANNING, cellBasedAction(ModRecipes.SOLID_CANNING_MACHINE, 0.2, 0.4, ModFluids.SULFUR, helper));

        helper.generate(cache, builder.build(), createNormalPath(path, createAppendID(id.getPath()), false));

    }

    @Override
    public void appendEnchanting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendFarming(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendFishing(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendHunting(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendMining(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder builder = Append.Builder.appender(id);
        builder.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("bauxite_ores", Registry.BLOCK))
                .reward(helper.moneyReward(3))
                .reward(helper.expReward(4))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("galena_ores", Registry.BLOCK))
                .reward(helper.moneyReward(3))
                .reward(helper.expReward(3.6))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("iridium_ores", Registry.BLOCK))
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(20))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("lead_ores", Registry.BLOCK))
                .reward(helper.moneyReward(4))
                .reward(helper.expReward(4))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("peridot_ores", Registry.BLOCK))
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(5))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("cinnabar_ores", Registry.BLOCK))
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(5))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("pyrite_ores", Registry.BLOCK))
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(5))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("ruby_ores", Registry.BLOCK))
                .block(createCommonTag("sapphire_ores", Registry.BLOCK))
                .block(createCommonTag("sheldonite_ores", Registry.BLOCK))
                .block(createCommonTag("silver_ores", Registry.BLOCK))
                .block(createCommonTag("sodalite_ores", Registry.BLOCK))
                .block(createCommonTag("tungsten_ores", Registry.BLOCK))
                .reward(helper.moneyReward(5))
                .reward(helper.expReward(5))
        ).addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(createCommonTag("sphalerite_ores", Registry.BLOCK))
                .block(createCommonTag("tin_ores", Registry.BLOCK))
                .reward(helper.moneyReward(2.5))
                .reward(helper.expReward(2.5))
        );
        helper.generate(cache, builder.build(), createNormalPath(path, createAppendID(id.getPath()), false));

    }

    @Override
    public void appendTrading(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendSmithing(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder builder = Append.Builder.appender(id);
        builder.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_HELMET, RUBY_HELMET, SAPPHIRE_HELMET)
                .reward(helper.moneyReward(20))
                .reward(helper.expReward(15))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_CHESTPLATE, RUBY_CHESTPLATE, SAPPHIRE_CHESTPLATE)
                .reward(helper.moneyReward(32))
                .reward(helper.expReward(24))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_LEGGINGS, RUBY_LEGGINGS, SAPPHIRE_LEGGINGS)
                .reward(helper.moneyReward(28))
                .reward(helper.expReward(21))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_BOOTS, RUBY_BOOTS, SAPPHIRE_BOOTS)
                .reward(helper.moneyReward(16))
                .reward(helper.expReward(12))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_SWORD, RUBY_SWORD, SAPPHIRE_SWORD)
                .reward(helper.moneyReward(8))
                .reward(helper.expReward(6))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_PICKAXE, RUBY_PICKAXE, SAPPHIRE_PICKAXE)
                .reward(helper.moneyReward(12))
                .reward(helper.expReward(9))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_SPADE, RUBY_SPADE, SAPPHIRE_SPADE)
                .reward(helper.moneyReward(4))
                .reward(helper.expReward(3))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_AXE, RUBY_AXE, SAPPHIRE_AXE)
                .reward(helper.moneyReward(12))
                .reward(helper.expReward(9))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(PERIDOT_HOE, RUBY_HOE, SAPPHIRE_HOE)
                .reward(helper.moneyReward(8))
                .reward(helper.expReward(6))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_CHESTPLATE, SILVER_CHESTPLATE)
                .reward(helper.moneyReward(4.5 * 8))
                .reward(helper.expReward(3.5 * 8))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_LEGGINGS, SILVER_LEGGINGS)
                .reward(helper.moneyReward(4.5 * 7))
                .reward(helper.expReward(3.5 * 7))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_BOOTS, SILVER_BOOTS)
                .reward(helper.moneyReward(4.5 * 4))
                .reward(helper.expReward(3.5 * 4))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_HELMET, SILVER_HELMET)
                .reward(helper.moneyReward(4.5 * 5))
                .reward(helper.expReward(3.5 * 5)));

        builder.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_SWORD)
                .reward(helper.moneyReward(4.5 * 2))
                .reward(helper.expReward(3.5 * 2))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_PICKAXE)
                .reward(helper.moneyReward(4.5 * 3))
                .reward(helper.expReward(3.5 * 3))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_SPADE)
                .reward(helper.moneyReward(4.5 * 1))
                .reward(helper.expReward(3.5 * 1))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_AXE)
                .reward(helper.moneyReward(4.5 * 3))
                .reward(helper.expReward(3.5 * 3))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(BRONZE_HOE)
                .reward(helper.moneyReward(4.5 * 2))
                .reward(helper.expReward(3.5 * 2))
        );
        builder.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(STEEL_HELMET)
                .reward(helper.moneyReward(6 * 5))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(STEEL_CHESTPLATE)
                .reward(helper.moneyReward(6 * 8))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(STEEL_LEGGINGS)
                .reward(helper.moneyReward(6 * 7))
        ).addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(STEEL_BOOTS)
                .reward(helper.moneyReward(6 * 4))
        );

        builder.addAction(TR_COMPRESSOR, TechRebornMachineAction.builder()
                .rebornRecipeType(ModRecipes.COMPRESSOR)
                .item(PLATES_TAG)
                .reward(helper.moneyReward(0.5))
                .reward(helper.expReward(0.5))
        )
                .addAction(TR_ALLOY_SMELTER, rebornAction(ModRecipes.ALLOY_SMELTER, Items.NETHERITE_INGOT, 6, 4, helper))
                .addAction(TR_ALLOY_SMELTER, rebornAction(ModRecipes.ALLOY_SMELTER, Ingots.ELECTRUM.asItem(), 3, 2, helper))
                .addAction(TR_ALLOY_SMELTER, rebornAction(ModRecipes.ALLOY_SMELTER, Ingots.BRONZE.asItem(), 2, 2, helper))
                .addAction(TR_ALLOY_SMELTER, rebornAction(ModRecipes.ALLOY_SMELTER, Ingots.BRASS.asItem(), 2, 2, helper))
                .addAction(TR_ALLOY_SMELTER, rebornAction(ModRecipes.ALLOY_SMELTER, Ingots.INVAR.asItem(), 3, 2, helper))
                .addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                        .item(Ingots.IRIDIUM_ALLOY.asItem())
                        .reward(helper.moneyReward(25))
                        .reward(helper.expReward(50)))

                .addAction(TR_IMPLOSION_COMPRESSOR, rebornAction(ModRecipes.IMPLOSION_COMPRESSOR, Gems.YELLOW_GARNET.asItem(), 0.2, 0.3, helper)
                        .item(SmallDusts.ENDER_EYE.asItem(), Dusts.DARK_ASHES.asItem(), Gems.SAPPHIRE.asItem(),
                                Gems.RUBY.asItem(), Gems.RED_GARNET.asItem(), Gems.PERIDOT.asItem(), Dusts.RED_GARNET.asItem()))
                .addAction(TR_IMPLOSION_COMPRESSOR, rebornAction(ModRecipes.IMPLOSION_COMPRESSOR, Plates.IRIDIUM_ALLOY.asItem(), 4, 6, helper))
                .addAction(TR_IMPLOSION_COMPRESSOR, rebornAction(ModRecipes.IMPLOSION_COMPRESSOR, Items.EMERALD, 0.2, 0.3, helper))
                .addAction(TR_IMPLOSION_COMPRESSOR, rebornAction(ModRecipes.IMPLOSION_COMPRESSOR, Items.AMETHYST_SHARD, 0.2, 0.5, helper))

                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.TUNGSTEN.asItem(), 0.1, 0.5, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.TITANIUM.asItem(), 0.1, 0.5, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.STEEL.asItem(), 0.1, 0.4, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.SILVER.asItem(), 0.1, 0.4, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.REFINED_IRON.asItem(), 0.1, 0.4, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.CHROME.asItem(), 0.1, 0.4, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.ALUMINUM.asItem(), 0.1, 0.4, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.HOT_TUNGSTENSTEEL.asItem(), 2, 5, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Ingots.IRIDIUM.asItem(), 2, 5, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Dusts.DARK_ASHES.asItem(), 0.05, 0.2, helper))
                .addAction(TR_BLAST_FURNACE, rebornAction(ModRecipes.BLAST_FURNACE, Dusts.DARK_ASHES.asItem(), 0.05, 0.2, helper))

                .addAction(TR_WIRE_MILL, rebornAction(ModRecipes.WIRE_MILL, Cables.TIN.asItem(), 0.2, 0.4, helper))
                .addAction(TR_WIRE_MILL, rebornAction(ModRecipes.WIRE_MILL, Cables.HV.asItem(), 0.2, 0.4, helper))
                .addAction(TR_WIRE_MILL, rebornAction(ModRecipes.WIRE_MILL, Cables.GOLD.asItem(), 0.2, 0.4, helper))
                .addAction(TR_WIRE_MILL, rebornAction(ModRecipes.WIRE_MILL, Cables.COPPER.asItem(), 0.2, 0.4, helper));

        helper.generate(cache, builder.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendLogging(CachedOutput cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    private TechRebornMachineAction.Builder centrifugeCellAction(double money, double exp, ModFluids fluids, ProviderHelpers helper) {
        return cellBasedAction(ModRecipes.CENTRIFUGE, money, exp, fluids, helper);
    }

    private TechRebornMachineAction.Builder electrolyzerCellAction(double money, double exp, ModFluids fluids, ProviderHelpers helper) {
        return cellBasedAction(ModRecipes.INDUSTRIAL_ELECTROLYZER, money, exp, fluids, helper);
    }

    private TechRebornMachineAction.Builder cellBasedAction(RebornRecipeType<?> type, double money, double exp, ModFluids fluids, ProviderHelpers helper) {
        return TechRebornMachineAction.builder()
                .rebornRecipeType(type)
                .item(CELL)
                .reward(helper.moneyReward(money))
                .reward(helper.expReward(exp))
                .nbt(DynamicCellItem.getCellWithFluid(fluids.getFluid()).getTag());
    }

    private TechRebornMachineAction.Builder rebornAction(RebornRecipeType<?> type, Item item, double money, double exp, ProviderHelpers helpers) {
        return TechRebornMachineAction.builder()
                .rebornRecipeType(type)
                .item(item)
                .reward(helpers.moneyReward(money))
                .reward(helpers.expReward(exp));
    }

}
