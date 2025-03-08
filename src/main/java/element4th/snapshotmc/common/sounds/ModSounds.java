package element4th.snapshotmc.common.sounds;

import element4th.snapshotmc.SnapshotMCMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

//nico. do your thing
public class ModSounds {
    public static final Identifier CUSTOM_SOUND_ID = Identifier.of(SnapshotMCMain.MOD_ID + ":boomerang_flying");
    public static final SoundEvent CUSTOM_SOUND = SoundEvent.of(CUSTOM_SOUND_ID);

    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, CUSTOM_SOUND_ID, CUSTOM_SOUND);
    }
}
