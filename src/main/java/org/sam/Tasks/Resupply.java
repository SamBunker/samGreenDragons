package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.Task;
import org.sam.Functions;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;
import org.sam.samGreenDragons;

import java.util.ArrayList;
import java.util.List;

public class Resupply extends Task {
    samGreenDragons main;

    public Resupply(samGreenDragons main) {
        super();
        super.name = "Going to Lumbridge Bank";
        this.main = main;
    }

    InventoryConfig inventoryConfig;
    EquipmentConfig equipmentConfig;

    @Override
    public boolean activate() {
        return Bank.inViewport()
                && Bank.nearest().reachable()
                && (Constants.LUMBRIDGE_AREA.contains(Players.local()) || Constants.FEROX_ENCLAVE.contains(Players.local()));
    }

    @Override
    public void execute() {
        if (Bank.stream().first().interact("Bank")) {
            Condition.wait(Bank::opened, 50, 40);

            Bank.depositInventory();

            List<Item> difference = Functions.getInventoryDifference();



        }
    }
}
