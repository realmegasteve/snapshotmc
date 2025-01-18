package element4th.snapshotmc.common.entity.custom.capybara;

import element4th.snapshotmc.SnapshotMCMain;
import element4th.snapshotmc.registry.SnapshotRegistry;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Objects;
import java.util.Optional;

public class CapybaraVariants {
    public static final RegistryKey<CapybaraVariant> DESERT = of("desert");
    public static final RegistryKey<CapybaraVariant> SNOW = of("snow");
    public static final RegistryKey<CapybaraVariant> NORMAL = of("normal");

    public CapybaraVariants() {
    }

    private static RegistryKey<CapybaraVariant> of(String id) {
        return RegistryKey.of(SnapshotRegistry.CAPYBARA_VARIANT_KEY, SnapshotMCMain.id(id));
    }

    static void register(Registerable<CapybaraVariant> registry, RegistryKey<CapybaraVariant> key, String textureName, RegistryKey<Biome> biome) {
        RegistryEntry<Biome> biomeEntry = registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biome);
        register(registry, key, textureName, RegistryEntryList.of(biomeEntry));
    }

    static void register(Registerable<CapybaraVariant> registry, RegistryKey<CapybaraVariant> key, String textureName, TagKey<Biome> biomeTag) {
        RegistryEntryList<Biome> biomeList = registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag);
        register(registry, key, textureName, biomeList);
    }

    static void register(Registerable<CapybaraVariant> registry, RegistryKey<CapybaraVariant> key, String textureName, RegistryEntryList<Biome> biomes) {
        Identifier identifier = SnapshotMCMain.id("entity/capybara/" + textureName);
        registry.register(key, new CapybaraVariant(identifier, biomes));
    }

    public static RegistryEntry<CapybaraVariant> fromBiome(DynamicRegistryManager dynamicRegistryManager, RegistryEntry<Biome> biome) {
        Registry<CapybaraVariant> registry = dynamicRegistryManager.get(SnapshotRegistry.CAPYBARA_VARIANT_KEY);
        Optional var10000 = registry.streamEntries().filter((entry) -> {
            return ((CapybaraVariant)entry.value()).getBiomes().contains(biome);
        }).findFirst().or(() -> {
            return registry.getEntry(NORMAL);
        });
        Objects.requireNonNull(registry);
        return (RegistryEntry)var10000.or(registry::getDefaultEntry).orElseThrow();
    }

    public static void bootstrap(Registerable<CapybaraVariant> registry) {
        register(registry, NORMAL, "normal", BiomeKeys.FOREST);
        register(registry, DESERT, "desert", BiomeKeys.DESERT);
        register(registry, SNOW, "snow", BiomeTags.SPAWNS_SNOW_FOXES);
    }
}
