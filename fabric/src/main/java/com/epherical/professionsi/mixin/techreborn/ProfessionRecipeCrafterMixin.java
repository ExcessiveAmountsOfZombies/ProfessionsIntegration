package com.epherical.professionsi.mixin.techreborn;

import com.epherical.professions.integration.cardinal.BlockEntityComponent;
import com.epherical.professions.integration.cardinal.PlayerOwning;
import com.epherical.professionsi.integrations.techreborn.TechRebornEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import reborncore.common.crafting.RebornRecipe;
import reborncore.common.crafting.RebornRecipeType;
import reborncore.common.recipes.RecipeCrafter;
import techreborn.blockentity.machine.GenericMachineBlockEntity;

@Mixin(RecipeCrafter.class)
public class ProfessionRecipeCrafterMixin {

    @Shadow
    public RebornRecipe currentRecipe;

    @Shadow
    public BlockEntity blockEntity;

    @Shadow
    public RebornRecipeType<?> recipeType;

    /**
     *
     * @param ci
     */
    @Inject(method = "updateEntity", slice = @Slice(
            from = @At(value = "INVOKE", target = "Ljava/util/ArrayList;<init>()V"),
            to = @At(value = "INVOKE", target = "Ljava/util/ArrayList;contains(Ljava/lang/Object;)Z")),
            at = @At(value = "INVOKE", target = "Lreborncore/common/crafting/RebornRecipe;getOutputs()Ljava/util/List;"))
    public void professions$takeCraft(CallbackInfo ci) {
        if (this.blockEntity instanceof GenericMachineBlockEntity) {
            try {
                PlayerOwning component = this.blockEntity.getComponent(BlockEntityComponent.PLAYER_OWNABLE);
                if (component.hasOwner()) {
                    TechRebornEvent.REBORN_MACHINE_FINISH_EVENT.invoker().onFinish(component.getPlacedBy(), this.currentRecipe.getOutputs(), this.blockEntity, this.recipeType, this.currentRecipe);
                }
            } catch (Exception ignored) {}
        }
    }


}
