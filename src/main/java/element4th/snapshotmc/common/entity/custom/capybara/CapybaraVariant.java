package element4th.snapshotmc.common.entity.custom.capybara;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import element4th.snapshotmc.registry.CapybaraVariantRegistry;
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
import java.util.function.UnaryOperator;

public class CapybaraVariant {
    public static final Codec<CapybaraVariant> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Identifier.CODEC.fieldOf("texture").forGetter(capybaraVariant -> capybaraVariant.textureId),
                            RegistryCodecs.entryList(RegistryKeys.BIOME).fieldOf("biomes").forGetter(CapybaraVariant::getBiomes)
                    )
                    .apply(instance, CapybaraVariant::new)
    );

    public static final PacketCodec<RegistryByteBuf, CapybaraVariant> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, CapybaraVariant::getTextureId,
            PacketCodecs.registryEntryList(RegistryKeys.BIOME), CapybaraVariant::getBiomes,
            CapybaraVariant::new
    );
    public static final Codec<RegistryEntry<CapybaraVariant>> ENTRY_CODEC = RegistryElementCodec.of(CapybaraVariantRegistry.CAPYBARA_VARIANT_KEY, CODEC);

    public static final PacketCodec<RegistryByteBuf, RegistryEntry<CapybaraVariant>> ENTRY_PACKET_CODEC = PacketCodecs.registryEntry(
            CapybaraVariantRegistry.CAPYBARA_VARIANT_KEY, PACKET_CODEC
    );

    private final Identifier textureId;
    private final RegistryEntryList<Biome> biomes;

    public CapybaraVariant(Identifier textureId, RegistryEntryList<Biome> biomes) {
        this.textureId = textureId;
        this.biomes = biomes;
    }

    private static Identifier getTexture(Identifier id) {
        return id.withPath((UnaryOperator<String>)(oldPath -> "textures/" + oldPath + ".png"));
    }

    public Identifier getTextureId() {
        return this.textureId;
    }

    public RegistryEntryList<Biome> getBiomes() {
        return this.biomes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            return !(o instanceof CapybaraVariant capybaraVariant)
                    ? false
                    : Objects.equals(this.textureId, capybaraVariant.textureId)
                    && Objects.equals(this.biomes, capybaraVariant.biomes);
        }
    }

    @Override
    public int hashCode() {
        int i = 1;
        i = 31 * i + this.textureId.hashCode();
        return 31 * i + this.biomes.hashCode();
    }
}
