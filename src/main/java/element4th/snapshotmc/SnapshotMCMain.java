package element4th.snapshotmc;

import nazario.liby.api.registry.auto.LibyRegistryLoader;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotMCMain implements ModInitializer {

    public static final String MOD_ID = "snapshot_mc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LibyRegistryLoader.load("element4th.snapshotmc", LOGGER);
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
