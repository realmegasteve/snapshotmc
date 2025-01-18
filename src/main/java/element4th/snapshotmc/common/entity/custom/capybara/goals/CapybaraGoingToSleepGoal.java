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
        this.capybaraEntity = (CapybaraEntity)mob;
        this.lowestY = -2;
        this.range = range;
        this.maxYDifference = maxYDifference;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        return !isDay(this.capybaraEntity.getWorld());
    }

    @Override
    public void stop() {
        super.stop();

        this.capybaraEntity.capybaraWakeUp();
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 40;
    }

    @Override
    protected void startMovingToTarget() {
        this.mob
                .getNavigation()
                .startMovingTo((double)this.targetPos.getX() - 0.5, (double)(this.targetPos.getY() + 1), (double)this.targetPos.getZ() - 0.5, this.speed);
    }

    @Override
    public void tick() {
        super.tick();

        if(isDay(this.capybaraEntity.getWorld())) {
            this.capybaraEntity.wakeUp();
            stop();
            return;
        }

        if (!this.hasReached()) {
            findTargetPos();
        } else {
            this.capybaraEntity.capybaraSleep(this.capybaraEntity.getBlockPos());
        }
    }

    protected boolean findTargetPos() {
        int range = this.range;
        int maxYDifference = this.maxYDifference;
        BlockPos blockPos = this.mob.getBlockPos();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int yOffset = this.lowestY; yOffset <= maxYDifference; yOffset++) {
            for (int searchRange = 0; searchRange < range; searchRange++) {
                for (int xOffset = -searchRange; xOffset <= searchRange; xOffset++) {
                    for (int zOffset = -searchRange; zOffset <= searchRange; zOffset++) {
                        mutable.set(blockPos.add(xOffset, yOffset, zOffset));
                        if (this.mob.isInWalkTargetRange(mutable) && this.isTargetPos(this.mob.getWorld(), mutable)) {
                            this.targetPos = mutable;
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.isAir(pos.up()) && (world.getBlockState(pos).isIn(BlockTags.WOOL) || world.getBlockState(pos).isIn(BlockTags.BEDS) || world.getBlockState(pos).getBlock().equals(Blocks.HAY_BLOCK));
    }

    public static boolean isDay(World world) {
        return (world.getTimeOfDay() > 1000 && world.getTimeOfDay() < 13000);
    }
}
