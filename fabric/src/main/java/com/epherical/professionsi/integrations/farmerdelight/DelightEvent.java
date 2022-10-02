package com.epherical.professionsi.integrations.farmerdelight;

import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.UUID;

public class DelightEvent {

    public static final Event<SkilletCook> SKILLET_COOK_EVENT = EventFactory.createArrayBacked(SkilletCook.class, calls -> (owner, smeltedItem, recipe, blockEntity) -> {
        for (SkilletCook call : calls) {
            call.onSkilletCook(owner, smeltedItem, recipe, blockEntity);
        }
    });


    public interface SkilletCook {
        void onSkilletCook(UUID owner, ItemStack smeltedItem, Recipe<?> recipe, SkilletBlockEntity blockEntity);
    }
}
