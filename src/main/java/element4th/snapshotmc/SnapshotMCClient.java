package element4th.snapshotmc;

import element4th.snapshotmc.common.block.SnapshotBlocks;
import nazario.liby.api.registry.auto.LibyEntrypoints;
import nazario.liby.api.registry.auto.LibyRegistryLoader;
import nazario.liby.api.registry.runtime.models.LibyModelRegistry;
import nazario.liby.api.util.LibyModelHelper;
import net.fabricmc.api.ClientModInitializer;

public class SnapshotMCClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyRegistryLoader.load("element4th.snapshotmc", SnapshotMCMain.LOGGER, LibyEntrypoints.CLIENT);

        LibyModelRegistry.register(
                LibyModelHelper.createBlockItem(SnapshotBlocks.ROPE_BLOCK)
        );
    }
}
