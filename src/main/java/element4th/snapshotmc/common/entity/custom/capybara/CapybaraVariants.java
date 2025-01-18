package element4th.snapshotmc.common.entity.custom.capybara;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.registry.CapybaraVariantRegistry;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

public class CapybaraVariants {
    //public static final CapybaraVariant WARM = CapybaraVariant.register("warm", SnapshotMCMain.id(""),      BiomeKeys.DESERT);
    //public static final CapybaraVariant COLD = CapybaraVariant.register("cold", SnapshotMCMain.id(""),      BiomeTags.SPAWNS_SNOW_FOXES);
    //public static final CapybaraVariant DEFAULT = CapybaraVariant.register("normal", SnapshotMCMain.id(""), BiomeKeys.PLAINS);

    public static final RegistryKey<CapybaraVariant> WARM = of("warm");
    public static final RegistryKey<CapybaraVariant> COLD = of("cold");
    public static final RegistryKey<CapybaraVariant> DEFAULT = of("normal");

    private static RegistryKey<CapybaraVariant> of(String id) {
        return RegistryKey.of(CapybaraVariantRegistry.CAPYBARA_VARIANT_KEY, SnapshotMCMain.id(id));
    }

    static void register(Registerable<CapybaraVariant> registry, RegistryKey<CapybaraVariant> key, String textureName, RegistryKey<Biome> biome) {
        register(registry, key, textureName, RegistryEntryList.of(registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biome)));
    }

    static void register(Registerable<CapybaraVariant> registry, RegistryKey<CapybaraVariant> key, String textureName, TagKey<Biome> biomeTag) {
        register(registry, key, textureName, registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag));
    }

    static void register(Registerable<CapybaraVariant> registry, RegistryKey<CapybaraVariant> key, String textureName, RegistryEntryList<Biome> biomes) {
        Identifier identifier = SnapshotMCMain.id("entity/capybara/" + textureName);
        registry.register(key, new CapybaraVariant(identifier, biomes));
    }

    public static RegistryEntry<CapybaraVariant> fromBiome(DynamicRegistryManager dynamicRegistryManager, RegistryEntry<Biome> biome) {
        Registry<CapybaraVariant> registry = CapybaraVariantRegistry.CAPYBARA_VARIANTS_REGISTRY;
        return (RegistryEntry<CapybaraVariant>)registry.streamEntries()
                .filter(entry -> ((CapybaraVariant)entry.value()).getBiomes().contains(biome))
                .findFirst()
                .or(() -> registry.getEntry(DEFAULT))
                .or(registry::getDefaultEntry)
                .orElseThrow();
    }

    public static void bootstrap(Registerable<CapybaraVariant> registry) {
        register(registry, WARM, "capybara_warm", BiomeKeys.SAVANNA);
        register(registry, COLD, "capybara_cold", BiomeTags.SPAWNS_SNOW_FOXES);
        register(registry, DEFAULT, "capybara_normal", BiomeKeys.PLAINS);
    }
}
