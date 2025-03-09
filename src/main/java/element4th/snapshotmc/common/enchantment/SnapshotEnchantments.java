package element4th.snapshotmc.common.enchantment;

import element4th.snapshotmc.SnapshotMCMain;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import nazario.liby.api.registry.auto.LibyEntrypoints;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

@LibyAutoRegister(method = "register", entrypoints = LibyEntrypoints.MAIN)
public class SnapshotEnchantments {

    // Boomerang
    public static final RegistryKey<Enchantment> BOOMERANG_BOUNCING_KEY = of("bouncing");
    //public static final RegistryEntry<Enchantment> BOOMERANG_BOUNCING_ENTRY = Registry.register()

    public static void register() {

    }

    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, SnapshotMCMain.id(id));
    }
}
