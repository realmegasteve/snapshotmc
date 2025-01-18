package element4th.snapshotmc.mixin;

import element4th.snapshotmc.common.entity.custom.CustomSleepRotationEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Mixin(GeoEntityRenderer.class)
public abstract class GeoEntityRendererMixin {

    @Inject(method = "applyRotations(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/util/math/MatrixStack;FFFF)V", at = @At(value = "TAIL"))
    public <T extends Entity & GeoAnimatable> void snapshotmc$applyRotations(T animatable, MatrixStack poseStack, float ageInTicks, float rotationYaw, float partialTick, float nativeScale, CallbackInfo ci) {
        if(animatable instanceof CustomSleepRotationEntity customSleepRotationEntity) {
            poseStack.multiply(customSleepRotationEntity.getCustomRotationForSleeping());
        }
    }
}
