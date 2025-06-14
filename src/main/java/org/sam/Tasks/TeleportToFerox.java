package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Movement;
import org.powbot.api.rt4.Players;
import org.sam.Constants;
import org.sam.Task;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;
import org.sam.samGreenDragons;

public class TeleportToFerox extends Task {
    samGreenDragons main;

    public TeleportToFerox(samGreenDragons main) {
        super();
        super.name = "Teleporting to Ferox Enclave";
        this.main = main;
    }
    InventoryConfig inventoryConfig;
    EquipmentConfig equipmentConfig;


    @Override
    public boolean activate() {
        return Constants.LUMBRIDGE_AREA.contains(Players.local()) && Inventory.stream().allMatch(inventoryConfig.getInventoryStream());
    }

    @Override
    public void execute() {
        Item ring = Inventory.stream().nameContains("Ring of dueling").first();
        if (ring.valid()) {
            if (ring.interact("Ferox Enclave")) {
                Condition.wait(() -> Constants.FEROX_ENCLAVE_TELEPORT_AREA.contains(Players.local()), 100, 100);
            }
        }
    }
}


