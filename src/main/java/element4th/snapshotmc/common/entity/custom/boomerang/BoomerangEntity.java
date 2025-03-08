package element4th.snapshotmc.common.entity.custom.boomerang;

import element4th.snapshotmc.common.item.SnapshotItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;



public class BoomerangEntity extends PersistentProjectileEntity {
    private static float MAX_DISTANCE = 15.0F; // Distance before returning
    private boolean returning = false;
    private int bounceCount = 0; // Track how many times it bounces
    private static final int MAX_BOUNCES = 3; // Limit to avoid infinite bouncing

    public BoomerangEntity(EntityType<? extends BoomerangEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();

        // If the owner is null, remove the entity
        if (this.getOwner() == null) {
            this.discard();
            return;
        }

        Vec3d ownerPos = getOwner().getPos();
        Vec3d boomerangPos = this.getPos();
        double distance = ownerPos.distanceTo(boomerangPos);

        // Phase 1: Travel outward
        if (!returning && (distance >= MAX_DISTANCE || bounceCount >= MAX_BOUNCES)) {
            returning = true;
        }

        // Phase 2: Return to player
        if (returning) {
            Vec3d returnVec = ownerPos.subtract(boomerangPos).normalize().multiply(0.8);
            this.setVelocity(returnVec);

            // Phase 3: Catch boomerang
            if (distance < 1.5) {
                //this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                if(getOwner() instanceof PlayerEntity player) {
                    if (!player.isCreative()) {
                        player.getInventory().insertStack(getItemStack());
                    }
                }
                this.discard();
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            // Do damage but keep flying
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            Entity hitEntity = entityHitResult.getEntity();

            if(hitEntity.getUuid().equals(getOwner().getUuid())) return;

            hitEntity.damage(this.getWorld().getDamageSources().mobProjectile(hitEntity, (LivingEntity)this.getOwner()), 5);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            // Bounce off walls instead of stopping
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            Direction hitSide = blockHitResult.getSide();
            Vec3d currentVelocity = this.getVelocity();

            // Reflect velocity based on which side was hit
            Vec3d reflectedVelocity = switch (hitSide) {
                case NORTH, SOUTH -> new Vec3d(currentVelocity.x, currentVelocity.y, -currentVelocity.z);
                case EAST, WEST -> new Vec3d(-currentVelocity.x, currentVelocity.y, currentVelocity.z);
                case UP, DOWN -> new Vec3d(currentVelocity.x, -currentVelocity.y, currentVelocity.z);
                default -> currentVelocity;
            };

            this.setVelocity(reflectedVelocity.multiply(0.8)); // Reduce speed slightly
            this.bounceCount++;
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return getItemStack();
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(SnapshotItems.BOOMERANG);
    }

    public void setWeaponStack(ItemStack stack) {
        this.setStack(stack);
    }
}
