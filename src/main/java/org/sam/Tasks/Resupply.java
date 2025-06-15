package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.Random;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.Task;
import org.sam.Functions;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;
import org.sam.samGreenDragons;
import java.util.Map;

public class Resupply extends Task {
    samGreenDragons main;
    public InventoryConfig inventoryconfig;
    public EquipmentConfig equipmentConfig;

    public Resupply(samGreenDragons main, InventoryConfig inventoryConfig, EquipmentConfig equipmentConfig) {
        super();
        super.name = "Resupplying";
        this.main = main;
        this.inventoryconfig = inventoryConfig;
        this.equipmentConfig = equipmentConfig;
    }

    @Override
    public boolean activate() {
        return (!Functions.getEquipmentDifference(equipmentConfig).isEmpty()
                || !Functions.getInventoryDifference(inventoryconfig).isEmpty())
                && Bank.opened()
                && (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local()) || Constants.FEROX_ENCLAVE.contains(Players.local()));
    }

    @Override
    public void execute() {
        if (Functions.missingEquipment(equipmentConfig.equipmentStream())) {

        }
        if (!Functions.getEquipmentDifference(equipmentConfig).isEmpty()) {
            Map<Integer, Integer> missingEquipmentItems = Functions.getEquipmentDifference(equipmentConfig);

            if (Bank.depositInventory()) {
                if (!missingEquipmentItems.isEmpty()) {
                    for (Map.Entry<Integer, Integer> entry : missingEquipmentItems.entrySet()) {
                        int itemId = entry.getKey();
                        int amount = entry.getValue();

                        Item bankItem = Bank.stream().id(itemId).first();
                        if (bankItem.valid()) {
                            if (!bankItem.inViewport()) {
                                Bank.scrollToItem(bankItem);
                                Condition.wait(bankItem::inViewport, 60, 20);
                            } else {
                                if (Bank.withdraw(itemId, amount)) {
                                    Condition.wait(() -> Inventory.stream().id(itemId).count(true) >= amount, 40, 300);
                                }
                            }
                        }
                    }
                }
                Bank.close();
                Condition.wait(() -> !Bank.opened(), 30, 150);
                Inventory.stream().filter(Item::valid)
                        .filter(item -> item.actions().contains("Wield") || item.actions().contains("Wear"))
                        .forEach(item -> {
                            item.interact(item.actions().contains("Wield") ? "Wield" : "Wear");
                            Condition.sleep(Random.nextInt(60, 120));
                        });
            }
        }

        if (!Functions.getInventoryDifference(inventoryconfig).isEmpty()) {
            Map<Integer, Integer> missingInventoryItems = Functions.getInventoryDifference(inventoryconfig);

            if (Bank.opened()) {
                Bank.open();
                Condition.wait(Bank::opened, 30, 150);
            }

            for (Map.Entry<Integer, Integer> entry : missingInventoryItems.entrySet()) {
                int itemId = entry.getKey();
                int amount = entry.getValue();

                Item bankItem = Bank.stream().id(itemId).first();
                if (bankItem.valid()) {
                    if (!bankItem.inViewport()) {
                        Bank.scrollToItem(bankItem);
                        Condition.wait(bankItem::inViewport, 60, 20);
                    } else {
                        if (Bank.withdraw(itemId, amount)) {
                            Condition.wait(() -> Inventory.stream().id(itemId).count(true) >= amount, 40, 300);
                        }
                    }
                }
            }
            Bank.close();
            Condition.wait(() -> !Bank.opened(), 30, 150);
        }
    }
}
