package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;
import org.sam.Constants;
import org.sam.samGreenDragons;

public class WalkToFeroxBank extends Task {
    samGreenDragons main;

    public WalkToFeroxBank(samGreenDragons main) {
        super();
        super.name = "Walking to Ferox Bank";
        this.main = main;
    }

    @Override
    public boolean activate() {
        return (Constants.FEROX_ENCLAVE_TELEPORT_AREA.contains(Players.local()));
    }

    @Override
    public void execute() {
        Movement.walkTo(Constants.FEROX_ENCLAVE_BANK_AREA.getRandomTile());
        Condition.wait(() -> Constants.FEROX_ENCLAVE_BANK_AREA.contains(Players.local()), 100, 300);
    }
}
