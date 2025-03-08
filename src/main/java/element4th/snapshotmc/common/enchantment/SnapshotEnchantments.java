package element4th.snapshotmc.common.enchantment;

import element4th.snapshotmc.SnapshotMCMain;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

@LibyAutoRegister
public class SnapshotEnchantments {

    // Boomerang
    public static final RegistryKey<Enchantment> BOOMERANG_RANGE = of("extended_range");
    public static final RegistryKey<Enchantment> BOOMERANG_BOUNCE_OFF = of("bounce_off");
    public static final RegistryKey<Enchantment> BOOMERANG_SPEED = of("air_drag");

    public static void register() {


    }

    private static RegistryKey<Enchantment> of(final String name) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, SnapshotMCMain.id(name));
    }
}
