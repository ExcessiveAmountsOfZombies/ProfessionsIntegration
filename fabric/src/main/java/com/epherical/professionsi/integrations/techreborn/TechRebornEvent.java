package com.epherical.professionsi.integrations.techreborn;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import reborncore.common.crafting.RebornRecipeType;

import java.util.Collection;
import java.util.UUID;

public class TechRebornEvent {

    public interface RebornMachineFinish {
        void onFinish(UUID owner, Collection<ItemStack> items, BlockEntity blockEntity, RebornRecipeType<?> rebornRecipeType, Recipe<?> recipe);
    }

    public static final Event<RebornMachineFinish> REBORN_MACHINE_FINISH_EVENT = EventFactory.createArrayBacked(RebornMachineFinish.class, calls -> (owner, items, blockEntity, rebornRecipeType, recipe) -> {
        for (RebornMachineFinish call : calls) {
            call.onFinish(owner, items, blockEntity, rebornRecipeType, recipe);
        }
    });


}
