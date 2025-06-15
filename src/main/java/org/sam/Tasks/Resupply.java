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
        return Bank.open()
                && (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local()) || Constants.FEROX_ENCLAVE.contains(Players.local()))
                && (!Functions.equipmentMatchesConfig(equipmentConfig)
                || !Functions.inventoryMatchesConfig(inventoryconfig));
    }

    @Override
    public void execute() {
        if (Bank.depositInventory()) {
            Map<Integer, Integer> missingEquipmentItems = Functions.getEquipmentDifference(equipmentConfig);
            Map<Integer, Integer> missingInventoryItems = Functions.getInventoryDifference(inventoryconfig);
            System.out.print("Equipment " + missingEquipmentItems);
            System.out.print("Inventory " + missingInventoryItems);
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
                            if (bankItem.interact("Withdraw-" + amount + " " + bankItem.name())) {
                                Condition.wait(Chat::pendingInput, 20, 100);
                                if (Chat.pendingInput()) {
                                    Chat.sendInput(String.valueOf(amount));
                                }
                            }
                        }
                        Condition.wait(() -> Inventory.stream().id(itemId).count(true) >= amount, 40, 300);
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

            Bank.open();
            Condition.wait(Bank::opened, 30, 150);

            if (!missingInventoryItems.isEmpty()) {
                for (Map.Entry<Integer, Integer> entry : missingInventoryItems.entrySet()) {
                    int itemId = entry.getKey();
                    int amount = entry.getValue();

                    Item bankItem = Bank.stream().id(itemId).first();
                    if (bankItem.valid()) {
                        if (amount == Integer.MAX_VALUE) {
                            bankItem.interact("Withdraw-All");
                        } else {
                            bankItem.interact("Withdraw-" + amount);
                        }
                        Condition.wait(() -> Inventory.stream().id(itemId).count(true) >= amount, 30, 200);
                    }
                }
            }
            Bank.close();
            Condition.wait(() -> !Bank.opened(), 30, 150);
        }
    }
}
