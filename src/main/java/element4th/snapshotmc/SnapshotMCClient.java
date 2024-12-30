package element4th.snapshotmc;

import nazario.liby.api.registry.auto.LibyEntrypoints;
import nazario.liby.api.registry.auto.LibyRegistryLoader;
import net.fabricmc.api.ClientModInitializer;

public class SnapshotMCClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyRegistryLoader.load("element4th.snapshotmc", SnapshotMCMain.LOGGER, LibyEntrypoints.CLIENT);
    }
}
