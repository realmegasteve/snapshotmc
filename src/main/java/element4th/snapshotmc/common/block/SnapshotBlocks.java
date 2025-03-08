package element4th.snapshotmc.common.block;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.block.custom.RopeBlock;
import element4th.snapshotmc.common.item.custom.RopeBlockItem;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import nazario.liby.api.registry.auto.LibyEntrypoints;
import nazario.liby.api.registry.helper.LibyBlockRegister;
import nazario.liby.api.registry.runtime.tags.LibyTagRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

@LibyAutoRegister
@LibyAutoRegister(method = "registerClient", entrypoints = LibyEntrypoints.CLIENT)
public class SnapshotBlocks {
    public static final LibyBlockRegister REGISTER = new LibyBlockRegister(SnapshotMCMain.MOD_ID);

    public static final Block ROPE_BLOCK = REGISTER.registerBlock("rope", new RopeBlock(AbstractBlock.Settings.copy(Blocks.LADDER).noCollision().luminance((s) -> 0)), RopeBlockItem::new);
    public static final Block DESERT_FLOWER = REGISTER.registerBlock("desert_flower", new DeadBushBlock(AbstractBlock.Settings.copy(Blocks.DEAD_BUSH)), new Item.Settings());

    public static void register() {
        LibyTagRegistry.addBlocks(Identifier.of("minecraft","climbable"), "Snapshot MC", ROPE_BLOCK);
    }

    public static void registerClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DESERT_FLOWER, RenderLayer.getCutout());
    }
}
