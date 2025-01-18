package element4th.snapshotmc.registry;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.common.entity.custom.capybara.CapybaraVariant;
import element4th.snapshotmc.common.entity.custom.capybara.CapybaraVariants;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryInfo;

@LibyAutoRegister
public class CapybaraVariantRegistry {
    public static final Registry<CapybaraVariant> CAPYBARA_VARIANTS_REGISTRY = FabricRegistryBuilder.createSimple(CapybaraVariant.class, SnapshotMCMain.id("capybara_variants")).buildAndRegister();
    public static final TrackedDataHandler<RegistryEntry<CapybaraVariant>> CAPYBARA_VARIANT_TRACKED_DATA = TrackedDataHandler.create(CapybaraVariant.ENTRY_PACKET_CODEC);
    public static final RegistryKey<Registry<CapybaraVariant>> CAPYBARA_VARIANT_KEY = RegistryKey.ofRegistry(SnapshotMCMain.id("capybara_variant"));

    public static RegistryEntry<CapybaraVariant> NORMAL_VARIANT;
    public static RegistryEntry<CapybaraVariant> WARM_VARIANT;
    public static RegistryEntry<CapybaraVariant> COLD_VARIANT;

    public static void register() {
        BuiltinRegistries.REGISTRY_BUILDER.addRegistry(CAPYBARA_VARIANT_KEY, CapybaraVariants::bootstrap);

        TrackedDataHandlerRegistry.register(CAPYBARA_VARIANT_TRACKED_DATA);

        COLD_VARIANT = ((MutableRegistry)CAPYBARA_VARIANTS_REGISTRY).add(CAPYBARA_VARIANT_KEY, CapybaraVariants.COLD, null);
        WARM_VARIANT = ((MutableRegistry)CAPYBARA_VARIANTS_REGISTRY).add(CAPYBARA_VARIANT_KEY, CapybaraVariants.WARM, null);
        NORMAL_VARIANT = ((MutableRegistry)CAPYBARA_VARIANTS_REGISTRY).add(CAPYBARA_VARIANT_KEY, CapybaraVariants.DEFAULT, RegistryEntryInfo.DEFAULT);
    }
}