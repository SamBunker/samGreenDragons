package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.Functions;
import org.sam.Task;
import org.sam.samGreenDragons;

public class UsePool extends Task {
    samGreenDragons main;
    GameObject pool = Objects.stream().id(Constants.FEROX_REFRESHMENT_POOL_ID).nearest().first();

    public UsePool(samGreenDragons main) {
        super();
        super.name = "Using Pool";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return Constants.FEROX_ENCLAVE.contains(Players.local()) && pool.reachable() && Players.local().healthPercent() < 95 && Movement.energyLevel() < 90;
    }

    @Override
    public void execute() {
        if (pool.valid()) {
            if (!pool.inViewport()) {
                Camera.turnTo(pool);
                Movement.walkTo(pool);
                return;
            }
            if (pool.inViewport() && pool.reachable()) {
                if (pool.interact("Drink")) {
                    Condition.wait(() -> pool.getTile().distanceTo(Players.local()) < 2, 80, 100);
                    Condition.sleep(Random.nextInt(85, 112));
                }
            }
        }
    }
}
