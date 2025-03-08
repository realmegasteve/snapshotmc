package element4th.snapshotmc.common.entity.custom.boomerang;

import element4th.snapshotmc.SnapshotMCMain;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BoomerangEntityRenderer extends EntityRenderer<BoomerangEntity> {
    public static final Identifier TEXTURE = SnapshotMCMain.id("textures/entity/boomerang/boomerang.png");
    private final ItemRenderer ITEM_RENDERER;
    private int renderTick;

    public BoomerangEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.ITEM_RENDERER = context.getItemRenderer();
    }

    @Override
    public void render(BoomerangEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        matrixStack.push();

        matrixStack.translate(0, 0.25, 0);

        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));

        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(renderTick*8f));

        BakedModel model = ITEM_RENDERER.getModel(entity.getItemStack(), entity.getWorld(), null, 0);
        ITEM_RENDERER.renderItem(entity.getItemStack(), ModelTransformationMode.FIXED, false, matrixStack, vertexConsumers, light, 0, model);
        matrixStack.pop();

        renderTick++;
    }

    @Override
    public Identifier getTexture(BoomerangEntity entity) {

        return TEXTURE;
    }
}
