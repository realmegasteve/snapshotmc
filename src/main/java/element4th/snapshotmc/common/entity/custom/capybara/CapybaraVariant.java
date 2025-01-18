package element4th.snapshotmc.common.entity.custom.capybara;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import element4th.snapshotmc.registry.SnapshotRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryElementCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.Objects;

public class CapybaraVariant {
    public static final Codec<CapybaraVariant> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("textures").forGetter(CapybaraVariant::getTextureId),
                    RegistryCodecs.entryList(RegistryKeys.BIOME).fieldOf("biomes").forGetter(CapybaraVariant::getBiomes)
            ).apply(instance, CapybaraVariant::new)
    );

    public static final PacketCodec<RegistryByteBuf, CapybaraVariant> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, CapybaraVariant::getTextureId,
            PacketCodecs.registryEntryList(RegistryKeys.BIOME), CapybaraVariant::getBiomes,
            CapybaraVariant::new
    );

    public static final Codec<RegistryEntry<CapybaraVariant>> ENTRY_CODEC = RegistryElementCodec.of(SnapshotRegistry.CAPYBARA_VARIANT_KEY, CODEC);
    public static final PacketCodec<RegistryByteBuf, RegistryEntry<CapybaraVariant>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(SnapshotRegistry.CAPYBARA_VARIANT_KEY, PACKET_CODEC);
    private final Identifier id;
    private final Identifier textureId;
    private final RegistryEntryList<Biome> biomes;

    public CapybaraVariant(Identifier id, RegistryEntryList<Biome> biomes) {
        this.id = id;
        this.textureId = getTextureId(id);
        this.biomes = biomes;
    }

    private static Identifier getTextureId(Identifier id) {
        return id.withPath((oldPath) -> {
            return "textures/" + oldPath + ".png";
        });
    }

    public Identifier getTextureId() {
        return this.textureId;
    }

    public RegistryEntryList<Biome> getBiomes() {
        return this.biomes;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CapybaraVariant)) {
            return false;
        } else {
            CapybaraVariant capybaraVariant = (CapybaraVariant)o;
            return Objects.equals(this.id, capybaraVariant.id) && Objects.equals(this.biomes, capybaraVariant.biomes);
        }
    }

    public int hashCode() {
        int i = 1;
        i = 31 * i + this.id.hashCode();
        i = 31 * i + this.biomes.hashCode();
        return i;
    }
}
