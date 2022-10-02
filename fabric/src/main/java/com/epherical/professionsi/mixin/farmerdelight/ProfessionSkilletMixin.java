package com.epherical.professionsi.mixin.farmerdelight;

import com.epherical.professions.integration.cardinal.BlockEntityComponent;
import com.epherical.professions.integration.cardinal.PlayerOwning;
import com.epherical.professionsi.integrations.farmerdelight.DelightEvent;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(SkilletBlockEntity.class)
public class ProfessionSkilletMixin {


    /**
     * https://github.com/newhoryzon/farmers-delight-fabric/blob/85f0704f0604fa4d6b187d952d2dcc793c62e043/src/main/java/com/nhoryzon/mc/farmersdelight/entity/block/SkilletBlockEntity.java#L104
     *
     * @author Thonk
     * @reason Add profession handler
     */
    @Inject(method = "cookAndOutputItems", locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            at = @At(value = "INVOKE", target = "Lcom/nhoryzon/mc/farmersdelight/entity/block/SkilletBlockEntity;getBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    public void professions$handleItemCooked(ItemStack cookingStack, CallbackInfo ci, SimpleContainer wrapper, Optional<CampfireCookingRecipe> recipe, ItemStack resultStack) {

        SkilletBlockEntity entity = (SkilletBlockEntity) (Object) this;
        PlayerOwning component = entity.getComponent(BlockEntityComponent.PLAYER_OWNABLE);
        if (component.hasOwner()) {
            DelightEvent.SKILLET_COOK_EVENT.invoker().onSkilletCook(component.getPlacedBy(), resultStack, recipe.get(), entity);
        }
    }
}
