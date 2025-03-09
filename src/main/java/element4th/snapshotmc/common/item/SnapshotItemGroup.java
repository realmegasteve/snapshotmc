package element4th.snapshotmc.common.item;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.block.SnapshotBlocks;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import nazario.liby.api.registry.itemgroup.LibyItemGroup;
import nazario.liby.api.registry.itemgroup.LibyItemGroupRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


@LibyAutoRegister
public class SnapshotItemGroup {

    public static final LibyItemGroup SNAPSHOT_ITEMS = new LibyItemGroup(SnapshotMCMain.id("snapshot_mc"), Text.translatable("itemGroup.snapshot_mc.name"));

    public static void register() {
        SNAPSHOT_ITEMS.setIcon(new ItemStack(SnapshotItems.BOOMERANG));
        SNAPSHOT_ITEMS.addItem(
                SnapshotItems.BOOMERANG,
                SnapshotItems.HAMMER,
                SnapshotItems.CAPYBARA_SPAWN_EGG,
                SnapshotItems.CHAINMAIL,
                SnapshotBlocks.ROPE_BLOCK,
                SnapshotBlocks.DESERT_FLOWER
        );

        LibyItemGroupRegistry.registerItemGroup(SNAPSHOT_ITEMS);
    }
}
