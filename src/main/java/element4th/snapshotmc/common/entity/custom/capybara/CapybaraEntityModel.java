package element4th.snapshotmc.common.entity.custom.capybara;

import element4th.snapshotmc.SnapshotMCMain;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

@SuppressWarnings("all")
public class CapybaraEntityModel extends GeoModel<CapybaraEntity> {

    @Override
    public Identifier getModelResource(CapybaraEntity capybaraEntity, @Nullable GeoRenderer<CapybaraEntity> renderer) {
        return this.getModelResource(capybaraEntity);
    }

    @Override
    public Identifier getModelResource(CapybaraEntity capybaraEntity) {
        return SnapshotMCMain.id("geo/entity/capybara.geo.json");
    }

    @Override
    public Identifier getTextureResource(CapybaraEntity capybaraEntity, @Nullable GeoRenderer<CapybaraEntity> renderer) {
        return this.getTextureResource(capybaraEntity);
    }

    @Override
    public Identifier getTextureResource(CapybaraEntity capybaraEntity) {
        String id = "textures/entity/capybara/capybara_normal";
        if(capybaraEntity.capybaraIsSleeping()) id += "_sleeping";
        return SnapshotMCMain.id(id + ".png");
    }

    @Override
    public Identifier getAnimationResource(CapybaraEntity capybaraEntity) {
        return SnapshotMCMain.id("animations/entity/capybara.animation.json");
    }

    @Override
    public boolean crashIfBoneMissing() {
        return false;
    }
}
