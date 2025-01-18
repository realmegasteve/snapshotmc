package element4th.snapshotmc.common.entity.custom.capybara;

import element4th.snapshotmc.SnapshotMCMain;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class CapybaraEntityModel extends GeoModel<CapybaraEntity> {

    @Override
    public Identifier getModelResource(CapybaraEntity capybaraEntity, @Nullable GeoRenderer<CapybaraEntity> renderer) {
        return SnapshotMCMain.id("geo/entity/capybara.geo.json");
    }

    @Override
    public Identifier getModelResource(CapybaraEntity capybaraEntity) {
        return SnapshotMCMain.id("geo/entity/capybara.geo.json");
    }

    @Override
    public Identifier getTextureResource(CapybaraEntity capybaraEntity, @Nullable GeoRenderer<CapybaraEntity> renderer) {
        return capybaraEntity.getVariant().value().getTextureId();
    }

    @Override
    public Identifier getTextureResource(CapybaraEntity capybaraEntity) {
        return capybaraEntity.getVariant().value().getTextureId();
    }

    @Override
    public Identifier getAnimationResource(CapybaraEntity capybaraEntity) {
        return SnapshotMCMain.id("animations/entity/capybara.geo.json");
    }
}
