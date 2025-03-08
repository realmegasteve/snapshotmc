package element4th.snapshotmc.mixin;

import element4th.snapshotmc.common.block.SnapshotBlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "applyClimbingSpeed", at = @At("TAIL"), cancellable = true)
    private void snapshotmc$applyClimbingSpeed(Vec3d motion, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if(entity.isClimbing()) {
            entity.onLanding();
            float speed = 0.15f;
            double d = MathHelper.clamp(motion.x, -speed, speed);
            double e = MathHelper.clamp(motion.z, -speed, speed);
            double g = Math.max(motion.y, -speed);
            if (g < 0.0 && !entity.getBlockStateAtPos().isOf(SnapshotBlocks.ROPE_BLOCK) && entity.isHoldingOntoLadder() && entity instanceof PlayerEntity) {
                g = 0.0;
            }
            motion = new Vec3d(d, g, e);
        }

        cir.setReturnValue(motion);
    }
}