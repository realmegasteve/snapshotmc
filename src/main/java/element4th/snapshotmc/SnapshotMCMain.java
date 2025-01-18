package element4th.snapshotmc;

import element4th.snapshotmc.common.entity.custom.capybara.CapybaraVariants;
import element4th.snapshotmc.registry.SnapshotRegistry;
import nazario.liby.api.registry.auto.LibyRegistryLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotMCMain implements ModInitializer, PreLaunchEntrypoint {

    public static final String MOD_ID = "snapshot_mc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LibyRegistryLoader.load("element4th.snapshotmc", LOGGER);

        BuiltinRegistries.REGISTRY_BUILDER.addRegistry(SnapshotRegistry.CAPYBARA_VARIANT_KEY, CapybaraVariants::bootstrap);
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void onPreLaunch() {

    }
}
