package com.epherical.professionsi.mixin.farmerdelight;

import com.epherical.professions.events.trigger.TriggerEvents;
import com.nhoryzon.mc.farmersdelight.entity.block.inventory.slot.CookingPotResultSlot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CookingPotResultSlot.class)
public class ProfessionCookingPotMixin {
    @Shadow @Final private Player player;

    /**
     * https://github.com/newhoryzon/farmers-delight-fabric/blob/85f0704f0604fa4d6b187d952d2dcc793c62e043/src/main/java/com/nhoryzon/mc/farmersdelight/entity/block/inventory/slot/CookingPotResultSlot.java#L47-L54
     * @author Thonk
     * @reason Using mixin on other peoples mods seems delicate, so we'll save a link to the last known commit for 1.18.2
     * if anything breaks, we can easily refer back and see what changed.
     */
    @Inject(method = "checkTakeAchievements", at = @At(value = "INVOKE", target = "Lcom/nhoryzon/mc/farmersdelight/entity/block/CookingPotBlockEntity;clearUsedRecipes(Lnet/minecraft/world/entity/player/Player;)V"))
    public void professions$handleCooking(ItemStack stack, CallbackInfo ci) {
        TriggerEvents.TAKE_SMELTED_ITEM_EVENT.invoker().onItemTake((ServerPlayer) player, stack);
    }

}
