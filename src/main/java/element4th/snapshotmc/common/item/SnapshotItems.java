package element4th.snapshotmc.common.item;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.entity.SnapshotEntities;
import element4th.snapshotmc.common.item.custom.BoomerangItem;
import element4th.snapshotmc.common.item.custom.HammerItem;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import nazario.liby.api.registry.auto.LibyEntrypoints;
import nazario.liby.api.registry.helper.LibyItemRegister;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;

@LibyAutoRegister
@LibyAutoRegister(method = "registerClient", entrypoints = LibyEntrypoints.CLIENT)
public class SnapshotItems {
    public static final LibyItemRegister REGISTER = new LibyItemRegister(SnapshotMCMain.MOD_ID);

    public static final Item CHAINMAIL = REGISTER.registerItem("chainmail", new Item(new Item.Settings()));
    public static final Item CAPYBARA_SPAWN_EGG = REGISTER.registerItem("capybara_spawn_egg", new SpawnEggItem(SnapshotEntities.CAPYBARA_TYPE, 0xba8041, 0x362e2a, new Item.Settings()));
    public static final Item HAMMER = REGISTER.registerItem("hammer", new HammerItem(
            ToolMaterials.STONE,
            new Item.Settings().attributeModifiers(HammerItem.createAttributeModifiers(ToolMaterials.STONE, 9, -3.5f))
    ));
    public static final Item BOOMERANG = REGISTER.registerItem("boomerang", new BoomerangItem(
            ToolMaterials.WOOD,
            new Item.Settings().attributeModifiers(BoomerangItem.createAttributeModifiers())
    ));

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entry -> {
            entry.addAfter(Items.GHAST_TEAR, CHAINMAIL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entry -> {
            entry.addAfter(Items.CAMEL_SPAWN_EGG, CAPYBARA_SPAWN_EGG);
        });
    }
}
