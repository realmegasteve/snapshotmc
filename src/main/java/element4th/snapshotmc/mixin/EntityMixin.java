package element4th.snapshotmc.mixin;

import net.minecraft.component.Component;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    public void snapshotmc$interact(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if(player.getStackInHand(hand).getItem() instanceof ShearsItem) {
            Entity thisEntity = (Entity)(Object)this;

            if(thisEntity.hasCustomName() && player.isSneaking()) {
                Text customName = thisEntity.getCustomName();

                ItemStack stack = new ItemStack(Items.NAME_TAG);

                stack.set(DataComponentTypes.CUSTOM_NAME, customName);

                thisEntity.setCustomName(null);

                thisEntity.getWorld().spawnEntity(new ItemEntity(thisEntity.getWorld(), thisEntity.getPos().getX(), thisEntity.getPos().getY(), thisEntity.getPos().getZ(), stack));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
