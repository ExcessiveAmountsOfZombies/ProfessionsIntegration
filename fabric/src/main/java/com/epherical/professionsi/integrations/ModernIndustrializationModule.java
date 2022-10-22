package com.epherical.professionsi.integrations;

import aztech.modern_industrialization.MIBlock;
import aztech.modern_industrialization.MIItem;
import aztech.modern_industrialization.ModernIndustrialization;
import aztech.modern_industrialization.materials.MIMaterials;
import aztech.modern_industrialization.materials.Material;
import aztech.modern_industrialization.materials.part.MIParts;
import aztech.modern_industrialization.materials.part.Part;
import com.epherical.professions.datagen.ProviderHelpers;
import com.epherical.professions.profession.action.Actions;
import com.epherical.professions.profession.action.builtin.blocks.BreakBlockAction;
import com.epherical.professions.profession.action.builtin.items.CraftingAction;
import com.epherical.professions.profession.editor.Append;
import com.google.gson.Gson;
import net.minecraft.core.Registry;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
import java.nio.file.Path;

public class ModernIndustrializationModule extends Module {

    public ModernIndustrializationModule() {
        super(ModernIndustrialization.MOD_ID);
    }

    @Override
    public void appendAlchemist(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendBuilder(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendCrafting(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {
        Append.Builder appender = Append.Builder.appender(id);
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MIItem.CIRCUIT_BOARD.asItem(), MIItem.INVAR_ROTARY_BLADE.asItem(),
                        MIItem.NOT_GATE.asItem(), MIItem.WRENCH.asItem())
                .reward(helper.moneyReward(0.5))
                .reward(helper.expReward(3)));
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MIItem.STEEL_UPGRADE.asItem(), MIItem.RESISTOR.asItem(),
                        MIItem.CAPACITOR.asItem(), MIItem.INDUCTOR.asItem(), MIItem.DIODE.asItem(),
                        MIItem.TRANSISTOR.asItem(), MIItem.AND_GATE.asItem(), MIItem.OR_GATE.asItem(),
                        MIItem.AIR_INTAKE.asItem(), MIItem.ARITHMETIC_LOGIC_UNIT.asItem(), MIItem.ULTRADENSE_METAL_BALL.asItem())
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(5)));
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MIItem.MOTOR.asItem(), MIItem.LARGE_MOTOR.asItem(), MIItem.PUMP.asItem(),
                        MIItem.LARGE_PUMP.asItem(), MIItem.PISTON.asItem(), MIItem.CONVEYOR.asItem(),
                        MIItem.ROBOT_ARM.asItem(), MIItem.OP_AMP.asItem(), MIItem.MEMORY_MANAGEMENT_UNIT.asItem(),
                        MIItem.STEAM_MINING_DRILL.asItem())
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(10)));
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MIItem.ADVANCED_MOTOR.asItem(), MIItem.LARGE_ADVANCED_MOTOR.asItem(),
                        MIItem.ADVANCED_PUMP.asItem(), MIItem.LARGE_ADVANCED_PUMP.asItem(),
                        MIItem.CIRCUIT.asItem(), MIItem.ELECTRONIC_CIRCUIT_BOARD.asItem(),
                        MIItem.ELECTRONIC_CIRCUIT.asItem(), MIItem.PROCESSING_UNIT.asItem(),
                        MIItem.DIGITAL_CIRCUIT.asItem(), MIItem.QUANTUM_CIRCUIT.asItem(),
                        MIItem.STEAM_MINING_DRILL.asItem(), MIItem.DIESEL_MINING_DRILL.asItem(),
                        MIItem.DIESEL_JETPACK.asItem())
                .reward(helper.moneyReward(3))
                .reward(helper.expReward(15)));
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(MIItem.GRAVICHESTPLATE.asItem())
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(60)));
        appender.addAction(Actions.CRAFTS_ITEM, CraftingAction.craft()
                .item(getFromPart(MIMaterials.BRONZE, MIParts.BARREL))
                .item(getFromPart(MIMaterials.STEEL, MIParts.BARREL))
                .item(getFromPart(MIMaterials.ALUMINUM, MIParts.BARREL))
                .item(getFromPart(MIMaterials.STAINLESS_STEEL, MIParts.BARREL))
                .item(getFromPart(MIMaterials.TITANIUM, MIParts.BARREL))
                .item(getFromPart(MIMaterials.ALUMINUM, MIParts.TANK))
                .item(getFromPart(MIMaterials.BRONZE, MIParts.TANK))
                .item(getFromPart(MIMaterials.STAINLESS_STEEL, MIParts.TANK))
                .item(getFromPart(MIMaterials.STEEL, MIParts.TANK))
                .item(getFromPart(MIMaterials.TITANIUM, MIParts.TANK))
                .item(MIMaterials.ALUMINUM.getPart(MIParts.MACHINE_CASING).asItem())
                .item(MIMaterials.ALUMINUM.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIMaterials.BLASTPROOF_ALLOY.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIMaterials.BRONZE.getPart(MIParts.MACHINE_CASING).asItem())
                .item(MIMaterials.BRONZE.getPart(MIParts.MACHINE_CASING_PIPE).asItem())
                .item(MIMaterials.BRONZE.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIMaterials.INVAR.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIMaterials.IRIDIUM.getPart(MIParts.MACHINE_CASING).asItem())
                .item(MIMaterials.IRIDIUM.getPart(MIParts.MACHINE_CASING_PIPE).asItem())
                .item(MIMaterials.IRIDIUM.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIMaterials.STAINLESS_STEEL.getPart(MIParts.MACHINE_CASING).asItem())
                .item(MIMaterials.STAINLESS_STEEL.getPart(MIParts.MACHINE_CASING_PIPE).asItem())
                .item(MIMaterials.STAINLESS_STEEL.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIMaterials.STEEL.getPart(MIParts.MACHINE_CASING).asItem())
                .item(MIMaterials.STEEL.getPart(MIParts.MACHINE_CASING_PIPE).asItem())
                .item(MIMaterials.TITANIUM.getPart(MIParts.MACHINE_CASING).asItem())
                .item(MIMaterials.TITANIUM.getPart(MIParts.MACHINE_CASING_PIPE).asItem())
                .item(MIMaterials.TITANIUM.getPart(MIParts.MACHINE_CASING_SPECIAL).asItem())
                .item(MIBlock.ADVANCED_MACHINE_HULL.asItem())
                .reward(helper.moneyReward(1))
                .reward(helper.expReward(5)));
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));
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
                .block(getOreTag(MIMaterials.ANTIMONY))
                .block(getOreTag(MIMaterials.BAUXITE))
                .block(getOreTag(MIMaterials.LEAD))
                .block(getOreTag(MIMaterials.NICKEL))
                .block(getOreTag(MIMaterials.SALT))
                .block(getOreTag(MIMaterials.TIN))
                .reward(helper.moneyReward(2))
                .reward(helper.expReward(4)));
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(getOreTag(MIMaterials.LIGNITE_COAL))
                .reward(helper.moneyReward(0.5))
                .reward(helper.expReward(1.5)));
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(getOreTag(MIMaterials.IRIDIUM))
                .reward(helper.moneyReward(10))
                .reward(helper.expReward(23)));
        appender.addAction(Actions.BREAK_BLOCK, BreakBlockAction.breakBlock()
                .block(getOreTag(MIMaterials.MOZANITE))
                .block(getOreTag(MIMaterials.URANIUM))
                .block(getOreTag(MIMaterials.TUNGSTEN))
                .reward(helper.moneyReward(3))
                .reward(helper.expReward(8)));
        helper.generate(gson, cache, appender.build(), createNormalPath(path, createAppendID(id.getPath()), false));
    }

    @Override
    public void appendTrading(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    @Override
    public void appendSmithing(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) throws IOException {

    }

    @Override
    public void appendLogging(HashCache cache, Path path, Gson gson, ProviderHelpers helper, ResourceLocation id) {

    }

    private TagKey<Block> getOreTag(Material material) {
        String taggedItemId = material.getPart(MIParts.ORE).getTaggedItemId();
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(taggedItemId.replace("#", "")));
    }

    private Item getFromPart(Material material, Part part) {
        return material.getPart(part).asItem();
    }
}
