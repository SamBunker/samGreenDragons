package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.Players;
import org.sam.*;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.sam.Functions.inventoryConfig;

public class Equip extends Task {
    samGreenDragons main;
    public EquipmentConfig equipmentConfig;
    public Variables vars;

    public Equip(samGreenDragons main, EquipmentConfig equipmentConfig) {
        super();
        super.name = "Equipping Gear";
        this.main = main;
        this.equipmentConfig = equipmentConfig;
    }

    @Override
    public boolean activate() {
        return vars.gearWithdrawn;
    }

    @Override
    public void execute() {
        int[] equipmentIds = equipmentConfig.equipmentStream();
        Inventory.stream().filter(Item::valid)
                .filter(Item::valid)
                .filter(item -> Arrays.stream(equipmentIds).anyMatch(id -> id == item.id()))
                .filter(item -> item.actions().contains("Wield") || item.actions().contains("Wear"))
                .forEach(item -> {
                    item.interact(item.actions().contains("Wield") ? "Wield" : "Wear");
                    Condition.sleep(Random.nextInt(60, 120));
                });

        vars.gearWithdrawn = false;
        vars.gearEquipped = true;
    }
}
