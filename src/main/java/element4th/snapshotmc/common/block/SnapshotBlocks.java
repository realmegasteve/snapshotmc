package element4th.snapshotmc.common.block;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.block.custom.RopeBlock;
import element4th.snapshotmc.common.item.custom.RopeBlockItem;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import nazario.liby.api.registry.helper.LibyBlockRegister;
import nazario.liby.api.registry.runtime.tags.LibyTagRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

@LibyAutoRegister
public class SnapshotBlocks {
    public static final LibyBlockRegister REGISTER = new LibyBlockRegister(SnapshotMCMain.MOD_ID);

    public static final Block ROPE_BLOCK = REGISTER.registerBlock("rope", new RopeBlock(AbstractBlock.Settings.copy(Blocks.LADDER).noCollision().luminance((s) -> 0)), RopeBlockItem::new);

    public static void register() {
        LibyTagRegistry.addBlocks(Identifier.of("minecraft","climbable"), "Snapshot MC", ROPE_BLOCK);
    }
}
