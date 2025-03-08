package element4th.snapshotmc.common.entity.custom.capybara.goals;

import element4th.snapshotmc.common.entity.custom.capybara.CapybaraEntity;
import net.minecraft.entity.ai.goal.Goal;

public class CapybaraWakeUpGoal extends Goal {
    private final CapybaraEntity capybaraEntity;

    public CapybaraWakeUpGoal(CapybaraEntity capybaraEntity) {
        this.capybaraEntity = capybaraEntity;
    }

    @Override
    public boolean canStart() {
        return CapybaraGoingToSleepGoal.isDay(this.capybaraEntity.getWorld()) && this.capybaraEntity.capybaraIsSleeping();
    }


    @Override
    public void tick() {
        if (!CapybaraGoingToSleepGoal.isDay(this.capybaraEntity.getWorld()) && this.capybaraEntity.capybaraIsSleeping()) {
            this.capybaraEntity.capybaraWakeUp();
            stop();
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        // Allow checking every tick for precise control
        return true;
    }
}
