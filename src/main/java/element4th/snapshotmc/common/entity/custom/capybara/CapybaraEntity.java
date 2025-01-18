package element4th.snapshotmc.common.entity.custom.capybara;

import element4th.snapshotmc.registry.SnapshotRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import java.util.Optional;

public class CapybaraEntity extends AnimalEntity implements VariantHolder<RegistryEntry<CapybaraVariant>>, GeoEntity {

    private static final TrackedData<RegistryEntry<CapybaraVariant>> VARIANT = DataTracker.registerData(CapybaraEntity.class, SnapshotRegistry.CAPYBARA_VARIANT_TRACKED_DATA);

    public CapybaraEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setVariant(RegistryEntry<CapybaraVariant> registryEntry) {
        this.dataTracker.set(VARIANT, registryEntry);
    }

    @Override
    public RegistryEntry<CapybaraVariant> getVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        Registry<CapybaraVariant> registry = SnapshotRegistry.CAPYBARA_VARIANT_REGISTRY;
        builder.add(VARIANT, registry.getEntry(CapybaraVariants.NORMAL).or(registry::getDefaultEntry).orElseThrow());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        this.getVariant().getKey().ifPresent(registryKey -> nbt.putString("variant", registryKey.getValue().toString()));
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Optional.ofNullable(Identifier.tryParse(nbt.getString("variant")))
                .map(variantId -> RegistryKey.of(SnapshotRegistry.CAPYBARA_VARIANT_KEY, variantId))
                .flatMap(variantKey -> this.getRegistryManager().get(SnapshotRegistry.CAPYBARA_VARIANT_KEY).getEntry(variantKey))
                .ifPresent(this::setVariant);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        RegistryEntry<Biome> registryEntry = world.getBiome(this.getBlockPos());
        RegistryEntry<CapybaraVariant> registryEntry2;
        if (entityData instanceof CapybaraEntity.CapybaraData data) {
            registryEntry2 = data.variant;
        } else {
            registryEntry2 = CapybaraVariants.fromBiome(this.getRegistryManager(), registryEntry);
            entityData = new CapybaraEntity.CapybaraData(registryEntry2);
        }

        this.setVariant(registryEntry2);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return null;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }

    public static class CapybaraData extends PassiveEntity.PassiveData {
        public final RegistryEntry<CapybaraVariant> variant;

        public CapybaraData(RegistryEntry<CapybaraVariant> variant) {
            super(false);
            this.variant = variant;
        }
    }
}
