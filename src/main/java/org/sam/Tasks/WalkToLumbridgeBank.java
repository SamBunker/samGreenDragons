package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Locatable;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.Task;
import org.sam.samGreenDragons;

public class WalkToLumbridgeBank extends Task {
    samGreenDragons main;

    public WalkToLumbridgeBank(samGreenDragons main) {
        super();
        super.name = "Walking to Lumbridge Bank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Constants.LUMBRIDGE_AREA.contains(Players.local()));
    }

    @Override
    public void execute() {
        GameObject stairs = Objects.stream().id(Constants.LUMBRIDGE_STAIRS_ID).nearest().first();

        if (stairs.valid()) {
            if (!stairs.inViewport()) {
                Camera.turnTo(stairs);
                Movement.walkTo(stairs);
                return;
            }
            if (stairs.inViewport() && stairs.reachable()) {
                if (stairs.interact("Top-floor")) {
                    Condition.wait(() -> stairs.getTile().distanceTo(Players.local()) < 2, 80, 100);
                    Condition.sleep(Random.nextInt(85, 112));
                }
            }
        }
        if (Bank.nearest().reachable()) {
            Movement.moveTo(Bank.nearest());
            Condition.wait(() -> Bank.nearest().tile().distanceTo(Players.local().tile()) < 3, 80, 100);
        }
    }
}
