package element4th.snapshotmc.common.sounds;

import element4th.snapshotmc.SnapshotMCMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

//nico. do your thing
public class ModSounds {
    public static final Identifier BOOMERANG_FLYING_ID = Identifier.of(SnapshotMCMain.MOD_ID + ":boomerang_flying");
    public static final SoundEvent BOOMERANG_FLYING = SoundEvent.of(BOOMERANG_FLYING_ID);
    public static final Identifier BOOMERANG_HIT_ID = Identifier.of(SnapshotMCMain.MOD_ID + ":boomerang_hit");
    public static final SoundEvent BOOMERANG_HIT = SoundEvent.of(BOOMERANG_HIT_ID);
    public static final Identifier HAMMER_HIT_ID = Identifier.of(SnapshotMCMain.MOD_ID + ":hammer_hit");
    public static final SoundEvent HAMMER_HIT = SoundEvent.of(HAMMER_HIT_ID);


    public static void registerSounds() {
        Registry.register(Registries.SOUND_EVENT, BOOMERANG_FLYING_ID, BOOMERANG_FLYING);
        Registry.register(Registries.SOUND_EVENT, BOOMERANG_HIT_ID, BOOMERANG_HIT);
        Registry.register(Registries.SOUND_EVENT, HAMMER_HIT_ID, HAMMER_HIT);
    }
}
