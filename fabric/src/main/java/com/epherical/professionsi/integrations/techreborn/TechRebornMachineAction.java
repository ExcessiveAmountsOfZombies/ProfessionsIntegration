package com.epherical.professionsi.integrations.techreborn;

import com.epherical.professions.ProfessionPlatform;
import com.epherical.professions.config.ProfessionConfig;
import com.epherical.professions.profession.Profession;
import com.epherical.professions.profession.ProfessionContext;
import com.epherical.professions.profession.ProfessionParameter;
import com.epherical.professions.profession.action.Action;
import com.epherical.professions.profession.action.ActionType;
import com.epherical.professions.profession.action.builtin.items.AbstractItemAction;
import com.epherical.professions.profession.conditions.ActionCondition;
import com.epherical.professions.profession.progression.Occupation;
import com.epherical.professions.profession.rewards.Reward;
import com.epherical.professions.profession.rewards.RewardType;
import com.epherical.professions.util.ActionDisplay;
import com.epherical.professions.util.ActionEntry;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import reborncore.common.crafting.RebornRecipeType;
import techreborn.init.ModRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TechRebornMachineAction extends AbstractItemAction {

    private static final ResourceLocation EMPTY = new ResourceLocation("minecraft:empty");

    public static final Map<RebornRecipeType<?>, ActionType> types = Maps.newHashMap();

    protected final ActionType actionType;
    protected final RebornRecipeType<?> recipeType;
    protected final ResourceLocation recipeID;
    protected final CompoundTag nbt;

    private List<ItemStack> realItems;

    protected TechRebornMachineAction(ActionCondition[] conditions, Reward[] rewards, List<ActionEntry<Item>> items, RebornRecipeType<?> type,
                                      ResourceLocation recipeID, CompoundTag nbt) {
        super(conditions, rewards, items);
        this.recipeType = type;
        ActionType value = types.get(recipeType);
        this.recipeID = recipeID;
        Validate.notNull(value);
        this.actionType = value;
        this.nbt = nbt;
    }

    @Override
    public boolean test(ProfessionContext professionContext) {
        ResourceLocation id = professionContext.getPossibleParameter(ProfessionParameter.RECIPE_CRAFTED).getId();
        if (recipeID != null && !recipeID.equals(EMPTY)) {
            return id.equals(recipeID);
        } else {
            RebornRecipeType<?> type = professionContext.getPossibleParameter(TechRebornModule.REBORN_RECIPE);
            if (nbt.size() > 0) {
                return type != null && type.equals(this.recipeType) && super.test(professionContext) && nbt.equals(professionContext.getPossibleParameter(ProfessionParameter.ITEM_INVOLVED).getTag());
            }
            return type != null && type.equals(this.recipeType) && super.test(professionContext);
        }
        // return recipe != null && type != null && type.equals(this.recipeType) &&
        // recipe.getId().equals(recipeID) && professionContext.getParameter(ProfessionParameter.ACTION_LOGGER).addSubjectOfAction(Component.nullToEmpty(""));
    }

    @Override
    public boolean internalCondition(ProfessionContext professionContext) {
        return true;
    }

    @Override
    public ActionType getType() {
        return actionType;
    }

    @Override
    public List<Singular<Item>> convertToSingle(Profession profession) {
        List<Action.Singular<Item>> list = new ArrayList<>();
        for (Item items : getRealItems()) {
            list.add(new TechRebornMachineAction.Single(items, profession));
        }
        return list;
    }

    @Override
    public Set<Item> getRealItems() {
        Set<Item> realItems = super.getRealItems();
        if (this.realItems == null) {
            this.realItems = new ArrayList<>();
            for (Item realItem : realItems) {
                ItemStack stack = new ItemStack(realItem);
                stack.setTag(nbt);
                this.realItems.add(stack);
            }
        }
        return realItems;
    }

    private List<ItemStack> getRealItemStacks() {
        getRealItems();
        return realItems;
    }

    public List<Component> displayInformation() {
        List<Component> components = new ArrayList<>();
        Map<RewardType, Component> map = this.getRewardInformation();

        for (ItemStack item : this.getRealItemStacks()) {
            components.add(((MutableComponent) item.getHoverName()).setStyle(Style.EMPTY.withColor(ProfessionConfig.descriptors)).append(ProfessionPlatform.platform.displayInformation(this, map)));
        }

        return components;
    }


    public List<ActionDisplay.Icon> clientFriendlyInformation(Component actionType) {
        List<ActionDisplay.Icon> components = new ArrayList<>();


        for (ItemStack item : this.getRealItemStacks()) {
            ActionDisplay.Icon icon = new ActionDisplay.Icon(item, ((MutableComponent) item.getHoverName()).setStyle(Style.EMPTY.withColor(ProfessionConfig.descriptors)), this.allRewardInformation(), actionType);
            components.add(icon);
        }

        return components;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Serializer extends AbstractItemAction.Serializer<TechRebornMachineAction> {

        @Override
        public void serialize(@NotNull JsonObject json, TechRebornMachineAction value, @NotNull JsonSerializationContext serializationContext) {
            super.serialize(json, value, serializationContext);
            if (!value.recipeID.equals(EMPTY)) {
                json.addProperty("recipe_id", value.recipeID.toString());
            }
            json.addProperty("recipe_type", Registry.RECIPE_TYPE.getKey(value.recipeType).toString());
            if (value.nbt.size() > 0) {
                json.add("nbt", Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, value.nbt));
            }
        }

        @Override
        public TechRebornMachineAction deserialize(JsonObject object, JsonDeserializationContext context, ActionCondition[] conditions, Reward[] rewards) {
            RebornRecipeType<?> type = (RebornRecipeType<?>) Registry.RECIPE_TYPE.getOptional(new ResourceLocation(GsonHelper.getAsString(object, "recipe_type"))).orElseThrow(() -> {
                return new JsonSyntaxException("could not deserialize unknown recipe type");
            });
            CompoundTag tag = new CompoundTag();
            if (object.has("nbt")) {
                tag = (CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, object.get("nbt"));
            }

            ResourceLocation recipeID = new ResourceLocation(GsonHelper.getAsString(object, "recipe_id", "minecraft:empty"));
            return new TechRebornMachineAction(conditions, rewards, deserializeItems(object), type, recipeID, tag);
        }
    }

    public static class Builder extends AbstractItemAction.Builder<TechRebornMachineAction.Builder> {
        private RebornRecipeType<?> recipeType;
        private ResourceLocation id = EMPTY;
        private CompoundTag tag = new CompoundTag();

        @Override
        protected Builder instance() {
            return this;
        }

        public Builder rebornRecipeType(RebornRecipeType<?> type) {
            this.recipeType = type;
            return this;
        }

        public Builder recipeId(ResourceLocation id) {
            this.id = id;
            return this;
        }

        public Builder recipeId(String id) {
            this.id = new ResourceLocation(id);
            return this;
        }

        public Builder nbt(CompoundTag tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public Action build() {
            return new TechRebornMachineAction(this.getConditions(), this.getRewards(), this.items, recipeType, id, tag);
        }
    }

    public class Single extends AbstractSingle<Item> {

        public Single(Item value, Profession profession) {
            super(value, profession);
        }

        @Override
        public ActionType getType() {
            return TechRebornMachineAction.this.getType();
        }

        @Override
        public Component createActionComponent() {
            return Component.translatable(getType().getTranslationKey());
        }

        @Override
        public boolean handleAction(ProfessionContext context, Occupation player) {
            return TechRebornMachineAction.this.handleAction(context, player);
        }

        @Override
        public void giveRewards(ProfessionContext context, Occupation occupation) {
            TechRebornMachineAction.this.giveRewards(context, occupation);
        }
    }

    static {
        types.put(ModRecipes.ALLOY_SMELTER, TechRebornModule.TR_ALLOY_SMELTER);
        types.put(ModRecipes.ASSEMBLING_MACHINE, TechRebornModule.TR_ASSEMBLING);
        types.put(ModRecipes.BLAST_FURNACE, TechRebornModule.TR_BLAST_FURNACE);
        types.put(ModRecipes.CENTRIFUGE, TechRebornModule.TR_CENTRIFUGE);
        types.put(ModRecipes.CHEMICAL_REACTOR, TechRebornModule.TR_CHEMICAL_REACTOR);
        types.put(ModRecipes.COMPRESSOR, TechRebornModule.TR_COMPRESSOR);
        types.put(ModRecipes.DISTILLATION_TOWER, TechRebornModule.TR_DISTILL);
        types.put(ModRecipes.EXTRACTOR, TechRebornModule.TR_EXTRACTOR);
        //types.put(ModRecipes.GRINDER, TechRebornModule.TR_INDUSTRIAL_GRINDER);
        types.put(ModRecipes.IMPLOSION_COMPRESSOR, TechRebornModule.TR_IMPLOSION_COMPRESSOR);
        types.put(ModRecipes.INDUSTRIAL_ELECTROLYZER, TechRebornModule.TR_INDUSTRIAL_ELECTROLYZER);
        types.put(ModRecipes.INDUSTRIAL_GRINDER, TechRebornModule.TR_INDUSTRIAL_GRINDER);
        types.put(ModRecipes.INDUSTRIAL_SAWMILL, TechRebornModule.TR_INDUSTRIAL_SAWMILL);
        //types.put(ModRecipes.RECYCLER,
        //types.put(ModRecipes.SCRAPBOX,
        types.put(ModRecipes.VACUUM_FREEZER, TechRebornModule.TR_VACUUM_FREEZER);
        types.put(ModRecipes.FLUID_REPLICATOR, TechRebornModule.TR_FLUID_REPLICATION);
        //types.put(ModRecipes.FUSION_REACTOR,
        // types.put(ModRecipes.ROLLING_MACHINE,
        types.put(ModRecipes.SOLID_CANNING_MACHINE, TechRebornModule.TR_SOLID_CANNING);
        types.put(ModRecipes.WIRE_MILL, TechRebornModule.TR_WIRE_MILL);
    }
}

