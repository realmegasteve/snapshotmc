package element4th.snapshotmc.common.entity.custom.capybara;

import element4th.snapshotmc.common.entity.SnapshotEntities;
import element4th.snapshotmc.common.entity.custom.capybara.goals.CapybaraGoingToSleepGoal;
import element4th.snapshotmc.common.entity.custom.capybara.goals.CapybaraSleepCycleGoal;
import element4th.snapshotmc.common.entity.custom.capybara.goals.CapybaraWakeUpGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Arm;
import org.joml.Vector3f;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Collections;

public class CapybaraEntity extends AnimalEntity implements GeoEntity {
    // Static animations
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("capybara.walk");
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("capybara.idle");
    protected static final RawAnimation SLEEPING_ANIM = RawAnimation.begin().thenLoop("capybara.sleeping");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public static final TrackedData<Boolean> IS_SLEEPING = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Vector3f> SLEEPING_POSITION = DataTracker.registerData(CapybaraEntity.class, TrackedDataHandlerRegistry.VECTOR3F);

    private int eatGrassTimer;
    private EatGrassGoal eatGrassGoal;

    private CapybaraSleepCycleGoal sleepCycleGoal;

    public CapybaraEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);

    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6f);
    }



    @Override
    protected void initGoals() {
        // Initialize AI goals
        this.eatGrassGoal = new EatGrassGoal(this);
        this.sleepCycleGoal = new CapybaraSleepCycleGoal(this, 1f, 32, 6);
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(3, sleepCycleGoal);
        this.goalSelector.add(4, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(5, new TemptGoal(this, 1.6, stack -> stack.isIn(ItemTags.PIG_FOOD), false));
        this.goalSelector.add(6, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, eatGrassGoal);
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(IS_SLEEPING, false);
        builder.add(SLEEPING_POSITION, new Vector3f(0.0F, 0.0F, 0.0F));
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        this.capybaraWakeUp();

        super.applyDamage(source, amount);
    }

    public boolean capybaraIsSleeping() {
        return this.getDataTracker().get(IS_SLEEPING);
    }

    public void capybaraSleep(BlockPos pos) {
        if (this.hasVehicle()) {
            this.stopRiding();
        }
        this.getDataTracker().set(IS_SLEEPING, true);
        this.getDataTracker().set(SLEEPING_POSITION, new Vector3f(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F));
        this.setVelocity(Vec3d.ZERO);
        this.velocityDirty = true;
    }


    @Override
    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();

        if(CapybaraSleepCycleGoal.isDay(this.getWorld()) && this.capybaraIsSleeping()) {
            //if(this.sleepCycleGoal == null) {
            //    this.sleepCycleGoal = new CapybaraSleepCycleGoal(this, 1f, 32, 6);
            //}

            this.sleepCycleGoal.start();
        }

        super.mobTick();
    }

    @Override
    public void tickMovement() {
        if (this.getWorld().isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }

        super.tickMovement();
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.SET_SHEEP_EAT_GRASS_TIMER_OR_PRIME_TNT_MINECART) {
            this.eatGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public boolean canMoveVoluntarily() {
        return super.canMoveVoluntarily() && !capybaraIsSleeping();
    }

    public void capybaraWakeUp() {
        System.out.println("WAKE UP WAKE UP WAKE UP");
        this.getDataTracker().set(IS_SLEEPING, false);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.PIG_FOOD);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return new CapybaraEntity(SnapshotEntities.CAPYBARA_TYPE, world);
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, this::defaultAnimationPredicate));
    }

    private PlayState defaultAnimationPredicate(AnimationState<CapybaraEntity> event) {
        var controller = event.getController();

        if (this.capybaraIsSleeping()) {
            controller.setAnimation(SLEEPING_ANIM);
            return PlayState.CONTINUE;
        }

        if (Math.floor(this.getVelocity().lengthSquared()) > 0.0 || event.isMoving()) {
            controller.setAnimation(WALK_ANIM);
        } else {
            controller.setAnimation(IDLE_ANIM);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("is_sleeping", this.capybaraIsSleeping());

        Vector3f sleepingPos = this.getDataTracker().get(SLEEPING_POSITION);
        if (sleepingPos != null) {
            NbtCompound sleepingPosNbt = new NbtCompound();
            sleepingPosNbt.putFloat("x", sleepingPos.x());
            sleepingPosNbt.putFloat("y", sleepingPos.y());
            sleepingPosNbt.putFloat("z", sleepingPos.z());
            nbt.put("sleeping_pos", sleepingPosNbt);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.getDataTracker().set(IS_SLEEPING, nbt.getBoolean("is_sleeping"));

        if (nbt.contains("sleeping_pos")) {
            NbtCompound sleepingPosNbt = nbt.getCompound("sleeping_pos");
            this.getDataTracker().set(SLEEPING_POSITION, new Vector3f(
                    sleepingPosNbt.getFloat("x"),
                    sleepingPosNbt.getFloat("y"),
                    sleepingPosNbt.getFloat("z")
            ));
        }
    }
}

