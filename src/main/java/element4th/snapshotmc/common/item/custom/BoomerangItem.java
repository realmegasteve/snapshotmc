package element4th.snapshotmc.common.item.custom;

import element4th.snapshotmc.common.entity.SnapshotEntities;
import element4th.snapshotmc.common.entity.custom.boomerang.BoomerangEntity;
import net.minecraft.block.BlockState;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class BoomerangItem extends ToolItem {
    public BoomerangItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder().build();
    }

    public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0F, 2);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR; // Optional: You could define a custom use action
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000; // Standard max use time for projectiles
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) return;

        int charge = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (charge < 10) return;

        // Spawn boomerang slightly in front of player to avoid collision issues
        Vec3d playerPos = playerEntity.getPos();
        Vec3d forward = playerEntity.getRotationVec(1.0F);
        Vec3d spawnPos = playerPos.add(forward.multiply(1.2)); // Spawn a little in front

        BoomerangEntity boomerangEntity = new BoomerangEntity(SnapshotEntities.BOOMERANG_TYPE, world);
        boomerangEntity.setPos(spawnPos.getX(), spawnPos.getY() + playerEntity.getEyeHeight(playerEntity.getPose()), spawnPos.getZ());
        boomerangEntity.setOwner(user);

        // Set proper velocity
        float speed = 2.5F;
        float divergence = 1.0F;
        Vec3d velocity = forward.multiply(speed).add(world.getRandom().nextGaussian() * 0.0075 * divergence,
                world.getRandom().nextGaussian() * 0.0075 * divergence,
                world.getRandom().nextGaussian() * 0.0075 * divergence);

        boomerangEntity.setVelocity(velocity.x, velocity.y, velocity.z);

        if (playerEntity.isCreative()) {
            boomerangEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
        } else {
            stack.damage(1, playerEntity, EquipmentSlot.MAINHAND);
        }

        boomerangEntity.setWeaponStack(stack.copy());

        if(!playerEntity.isCreative()) stack.setCount(0);
        world.spawnEntity(boomerangEntity);
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
                SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        BoomerangEntity boomerangEntity = new BoomerangEntity(SnapshotEntities.BOOMERANG_TYPE, world);
        boomerangEntity.setPos(pos.getX(), pos.getY(), pos.getZ());
        boomerangEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return boomerangEntity;
    }
}
