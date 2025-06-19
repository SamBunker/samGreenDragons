package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.sam.Constants;
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
        return (Constants.LUMBRIDGE_AREA_BOTTOM.contains(Players.local()) && !Constants.LUMBRIDGE_AREA_TOP.contains(Players.local()));
    }

    @Override
    public void execute() {
        if (Constants.LUMBRIDGE_AREA_BOTTOM.contains(Players.local())) {
            GameObject stairs = Objects.stream().id(Constants.LUMBRIDGE_STAIRS_ID).nearest().first();
            if (!stairs.valid() || !stairs.inViewport()) {
                Movement.step(Constants.LUMBRIDGE_STAIRS);
                return;
            }
            if (stairs.inViewport() && stairs.reachable()) {
                if (stairs.interact("Top-floor")) {
                    Condition.wait(() -> stairs.getTile().distanceTo(Players.local()) < 2, 80, 100);
                    Condition.sleep(Random.nextInt(85, 112));
                    return;
                }
            }
        }
        if (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local())) {
            Movement.step(Constants.LUMBRIDGE_BANK_AREA.getRandomTile());
            Condition.wait(() -> Constants.LUMBRIDGE_BANK_AREA.contains(Players.local()), 80, 100);
        }
    }
}
