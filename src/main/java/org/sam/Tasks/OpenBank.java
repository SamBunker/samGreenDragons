package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.Functions;
import org.sam.Task;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;
import org.sam.samGreenDragons;

public class OpenBank extends Task {
    samGreenDragons main;
    public InventoryConfig inventoryconfig;
    public EquipmentConfig equipmentConfig;

    public OpenBank(samGreenDragons main, InventoryConfig inventoryConfig, EquipmentConfig equipmentConfig) {
        super();
        super.name = "Opening Bank";
        this.main = main;
        this.inventoryconfig = inventoryConfig;
        this.equipmentConfig = equipmentConfig;
    }

    @Override
    public boolean activate() {
        return !Bank.opened()
                && (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local()) || Constants.FEROX_ENCLAVE.contains(Players.local()))
                && (Functions.missingEquipment(equipmentConfig.equipmentStream()).length > 0
                || !Functions.inventoryMatchesConfig(inventoryconfig));
    }

    @Override
    public void execute() {
        if ((Functions.missingEquipment(equipmentConfig.equipmentStream()).length > 0 || !Functions.inventoryMatchesConfig(inventoryconfig))) {
            if (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local())) {
                GameObject bank = Objects.stream().id(Constants.LUMBRIDGE_BANK).nearest().first();
                if (!bank.inViewport()) {
                    Camera.turnTo(bank);
                    Movement.step(bank);
                    Condition.wait(() -> bank.tile().distanceTo(Players.local().tile()) < 4, 80, 100);
                } else {
                    bank.interact("Bank");
                    Condition.wait(Bank::opened, 100, 100);
                    return;
                }
            }
            if (Constants.FEROX_ENCLAVE.contains(Players.local())) {
                GameObject bank = Objects.stream().id(Constants.LUMBRIDGE_BANK).nearest().first();
            }
        }
    }
}