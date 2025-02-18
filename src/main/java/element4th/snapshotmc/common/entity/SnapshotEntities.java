package element4th.snapshotmc.common.entity;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.entity.custom.boomerang.BoomerangEntity;
import element4th.snapshotmc.common.entity.custom.boomerang.BoomerangEntityRenderer;
import element4th.snapshotmc.common.entity.custom.capybara.CapybaraEntity;
import element4th.snapshotmc.common.entity.custom.capybara.CapybaraEntityRenderer;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import nazario.liby.api.registry.auto.LibyEntrypoints;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("unchecked")
@LibyAutoRegister
@LibyAutoRegister(method = "registerClient", entrypoints = LibyEntrypoints.CLIENT)
public class SnapshotEntities {

    public static final EntityType<? extends CapybaraEntity> CAPYBARA_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            SnapshotMCMain.id("capybara"),
            EntityType.Builder.create(CapybaraEntity::new, SpawnGroup.AMBIENT).dimensions(1f, 1f).build()
    );

    public static final EntityType<? extends BoomerangEntity> BOOMERANG_TYPE = (EntityType<? extends BoomerangEntity>) Registry.register(
            Registries.ENTITY_TYPE,
            SnapshotMCMain.id("boomerang"),
            EntityType.Builder.create(BoomerangEntity::new, SpawnGroup.AMBIENT).dimensions(0.8f, 0.5f).build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(CAPYBARA_TYPE, CapybaraEntity.createAttributes());
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        EntityRendererRegistry.register(CAPYBARA_TYPE, CapybaraEntityRenderer::new);
        EntityRendererRegistry.register(BOOMERANG_TYPE, BoomerangEntityRenderer::new);
    }
}
