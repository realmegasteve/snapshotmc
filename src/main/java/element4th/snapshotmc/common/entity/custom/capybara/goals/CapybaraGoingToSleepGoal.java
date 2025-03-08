package element4th.snapshotmc.common.entity.custom.capybara.goals;

import element4th.snapshotmc.common.entity.custom.capybara.CapybaraEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class CapybaraGoingToSleepGoal extends MoveToTargetPosGoal {
    private final CapybaraEntity capybaraEntity;

    private final int range;
    private final int maxYDifference;

    public CapybaraGoingToSleepGoal(PathAwareEntity mob, double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
        this.range = range;
        this.maxYDifference = maxYDifference;

        this.capybaraEntity = (CapybaraEntity) mob;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return !isDay(this.capybaraEntity.getWorld()) && this.findTargetPos();
    }

    @Override
    protected void startMovingToTarget() {
        this.mob.getNavigation().startMovingTo(
                this.targetPos.getX() + 0.5,
                this.targetPos.getY() + 1,
                this.targetPos.getZ() + 0.5,
                this.speed
        );
    }

    @Override
    public void tick() {
        super.tick();

        if (isDay(this.capybaraEntity.getWorld()) || capybaraEntity.capybaraIsSleeping()) {
            this.stop(); // Ends the goal if it's daytime
            return;
        }

        if (this.hasReached() && !this.capybaraEntity.capybaraIsSleeping()) {
            if (this.capybaraEntity.hurtTime <= 0) {
                this.capybaraEntity.capybaraSleep(this.targetPos);
                stop();
            }
        } else {
            this.findTargetPos(); // Keep searching for a valid target if not reached
        }
    }

    @Override
    protected boolean findTargetPos() {
        BlockPos currentPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int yOffset = -this.maxYDifference; yOffset <= this.maxYDifference; yOffset++) {
            for (int xOffset = -this.range; xOffset <= this.range; xOffset++) {
                for (int zOffset = -this.range; zOffset <= this.range; zOffset++) {
                    mutable.set(currentPos.getX() + xOffset, currentPos.getY() + yOffset, currentPos.getZ() + zOffset);
                    if (this.mob.isInWalkTargetRange(mutable) && this.isTargetPos(this.mob.getWorld(), mutable)) {
                        this.targetPos = mutable.toImmutable();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.isAir(pos.up()) && (
                world.getBlockState(pos).isIn(BlockTags.WOOL) ||
                        world.getBlockState(pos).isIn(BlockTags.BEDS) ||
                        world.getBlockState(pos).getBlock().equals(Blocks.HAY_BLOCK)
        );
    }

    public static boolean isDay(World world) {
        long timeOfDay = world.getTimeOfDay() % 24000; // Time wraps every day
        return timeOfDay > 1000 && timeOfDay < 13000;
    }
}
