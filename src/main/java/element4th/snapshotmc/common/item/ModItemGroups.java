package element4th.snapshotmc.common.item;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.block.SnapshotBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModItemGroups {
    public static void registerItemGroups() {
        final ItemGroup SNAPSHOT_MC = Registry.register(Registries.ITEM_GROUP,
                Identifier.of(SnapshotMCMain.MOD_ID, "snapshot_mc"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(SnapshotItems.BOOMERANG))
                        .displayName(Text.translatable("itemGroup.snapshot_mc"))
                        .entries(((displayContext, entries) -> {
                            entries.add(new ItemStack(SnapshotItems.BOOMERANG));
                            entries.add(new ItemStack(SnapshotItems.HAMMER));
                            entries.add(new ItemStack(SnapshotBlocks.ROPE_BLOCK));
                            entries.add(new ItemStack(SnapshotItems.CAPYBARA_SPAWN_EGG));
                            entries.add(new ItemStack(SnapshotItems.CHAINMAIL));
                        }))
                        .build())
    ;

        SnapshotMCMain.LOGGER.info("Registering item groups");

};
}
