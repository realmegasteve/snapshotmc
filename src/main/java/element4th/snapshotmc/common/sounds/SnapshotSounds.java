package element4th.snapshotmc.common.sounds;

import element4th.snapshotmc.SnapshotMCMain;
import nazario.liby.api.registry.auto.LibyAutoRegister;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

@LibyAutoRegister
public class SnapshotSounds {
    public static final SoundEvent BOOMERANG_FLYING = registerSoundEvent("boomerang_flying");
    public static final SoundEvent BOOMERANG_HIT = registerSoundEvent("boomerang_hit");
    public static final SoundEvent HAMMER_HIT = registerSoundEvent("hammer_hit");


    public static void register() {

    }

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = SnapshotMCMain.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}
