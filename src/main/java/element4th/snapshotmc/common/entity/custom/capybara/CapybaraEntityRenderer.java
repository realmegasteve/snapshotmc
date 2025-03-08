package element4th.snapshotmc.common.entity.custom.capybara;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CapybaraEntityRenderer extends GeoEntityRenderer<CapybaraEntity> {
    public CapybaraEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CapybaraEntityModel());
    }

    @Override
    public void render(CapybaraEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if(entity.isBaby()) withScale(0.5f);
        else withScale(1);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public float getMotionAnimThreshold(CapybaraEntity animatable) {
        return 0.010f;
    }
}
