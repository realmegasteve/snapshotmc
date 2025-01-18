package element4th.snapshotmc.registry;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.entity.custom.capybara.CapybaraVariant;
import element4th.snapshotmc.common.entity.custom.capybara.CapybaraVariants;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryInfo;

@LibyAutoRegister
public class SnapshotRegistry {

    public static final RegistryKey<Registry<CapybaraVariant>> CAPYBARA_VARIANT_KEY = RegistryKey.ofRegistry(SnapshotMCMain.id("capybara_variant"));
    public static final Registry<CapybaraVariant> CAPYBARA_VARIANT_REGISTRY =
            FabricRegistryBuilder.createSimple(CAPYBARA_VARIANT_KEY)
                    .attribute(RegistryAttribute.MODDED)
                    .buildAndRegister();

    public static final TrackedDataHandler<RegistryEntry<CapybaraVariant>> CAPYBARA_VARIANT_TRACKED_DATA = TrackedDataHandler.create(CapybaraVariant.ENTRY_PACKET_CODEC);

    public static void register() {
        ((MutableRegistry)Registries.REGISTRIES).add(CAPYBARA_VARIANT_KEY, CAPYBARA_VARIANT_REGISTRY, RegistryEntryInfo.DEFAULT);

        ((MutableRegistry)CAPYBARA_VARIANT_REGISTRY).add(CAPYBARA_VARIANT_KEY, CapybaraVariants.NORMAL, RegistryEntryInfo.DEFAULT);
        ((MutableRegistry)CAPYBARA_VARIANT_REGISTRY).add(CAPYBARA_VARIANT_KEY, CapybaraVariants.DESERT, RegistryEntryInfo.DEFAULT);
        ((MutableRegistry)CAPYBARA_VARIANT_REGISTRY).add(CAPYBARA_VARIANT_KEY, CapybaraVariants.SNOW, RegistryEntryInfo.DEFAULT);
    }
}