package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;
import org.sam.Constants;
import org.sam.Utils.Functions;
import org.sam.Configs.EquipmentConfig;
import org.sam.Configs.InventoryConfig;
import org.sam.samGreenDragons;

public class TeleportToFerox extends Task {
    samGreenDragons main;
    public InventoryConfig inventoryconfig;
    public EquipmentConfig equipmentConfig;

    public TeleportToFerox(samGreenDragons main, InventoryConfig inventoryConfig, EquipmentConfig equipmentConfig) {
        super();
        super.name = "Teleporting to Ferox Enclave";
        this.main = main;
        this.inventoryconfig = inventoryConfig;
        this.equipmentConfig = equipmentConfig;
    }

    @Override
    public boolean activate() {
        return (Functions.missingEquipment(equipmentConfig.equipmentStream()).length > 0
                && Functions.getInventoryDifference(inventoryconfig).isEmpty())
                && Constants.LUMBRIDGE_AREA_TOP.contains(Players.local())
                && !Constants.FEROX_ENCLAVE.contains(Players.local());
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


