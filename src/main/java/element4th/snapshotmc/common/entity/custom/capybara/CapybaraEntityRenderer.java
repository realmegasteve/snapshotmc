package element4th.snapshotmc.common.entity.custom.capybara;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CapybaraEntityRenderer extends GeoEntityRenderer<CapybaraEntity> {
    public CapybaraEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CapybaraEntityModel());
    }
}
