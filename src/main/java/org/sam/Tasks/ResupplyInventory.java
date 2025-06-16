package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.sam.Constants;
import org.sam.Task;
import org.sam.Functions;
import org.sam.Tasks.Configs.InventoryConfig;
import org.sam.samGreenDragons;

import java.util.Map;

public class ResupplyInventory extends Task {
    samGreenDragons main;
    public InventoryConfig inventoryconfig;

    public ResupplyInventory(samGreenDragons main, InventoryConfig inventoryConfig) {
        super();
        super.name = "Resupplying Inventory";
        this.main = main;
        this.inventoryconfig = inventoryConfig;
    }

    @Override
    public boolean activate() {
        return  inventoryconfig.gearEquipped
                && Bank.opened()
                && (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local()) || Constants.FEROX_ENCLAVE.contains(Players.local()));
    }

    @Override
    public void execute() {
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
                            //missingInventoryItems.remove(itemId);
                        }
                    }
                }
            }
            Bank.close();
            Condition.wait(() -> !Bank.opened(), 30, 150);
            inventoryconfig.gearEquipped = false;
        }
    }
}
