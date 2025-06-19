package org.sam.Tasks;

import org.powbot.api.Condition;
import org.powbot.api.rt4.*;
import org.powbot.mobile.rlib.generated.RItemDefinition;
import org.powbot.mobile.rscache.loader.ItemLoader;
import org.sam.*;
import org.sam.Constants;
import org.sam.Configs.EquipmentConfig;
import org.sam.Utils.Functions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResupplyGear extends Task {
    samGreenDragons main;
    public EquipmentConfig equipmentConfig;
    public Variables vars;

    public ResupplyGear(samGreenDragons main, EquipmentConfig equipmentConfig) {
        super();
        super.name = "Resupplying Gear";
        this.main = main;
        this.equipmentConfig = equipmentConfig;
    }

    @Override
    public boolean activate() {
        return Functions.missingEquipment(equipmentConfig.equipmentStream()).length > 0
                && Bank.opened()
                && (Constants.LUMBRIDGE_AREA_TOP.contains(Players.local()) || Constants.FEROX_ENCLAVE.contains(Players.local()));
    }

    @Override
    public void execute() {
        if (Functions.missingEquipment(equipmentConfig.equipmentStream()).length > 0) {
            int[] missingEquipmentItems = Functions.missingEquipment(equipmentConfig.equipmentStream());
            HashMap<Integer, Integer> grabEquipmentFromBank = new HashMap<>();
            List<String> ammoTypes = Arrays.asList("arrow", "bolt", "javelin", "dart", "brutal");

            for (int itemId : missingEquipmentItems) {
                RItemDefinition itemDef = ItemLoader.lookup(itemId);
                String name = itemDef != null ? itemDef.name().toLowerCase() : "";
                boolean isAmmo = ammoTypes.stream().anyMatch(name::contains);
                int count = isAmmo ? equipmentConfig.getQuiverStackSize() : 1;
                grabEquipmentFromBank.put(itemId, count);
            }

            for (Map.Entry<Integer, Integer> entry : grabEquipmentFromBank.entrySet()) {
                int itemId = entry.getKey();
                int amount = entry.getValue();

                for (int i = 0; i < 20; i++) {
                    System.out.println("Looking for item: " + itemId + ", amount: " + amount + "\n");
                }
                Item bankItem = Bank.stream().id(itemId).first();
                if (bankItem.valid()) {
                    if (!bankItem.inViewport()) {
                        Bank.scrollToItem(bankItem);
                        Condition.wait(bankItem::inViewport, 60, 20);
                    } else {
                        if (!Inventory.stream().isEmpty()) {
                            Bank.deposit(Inventory.stream().action("Eat").first().id(), 5);
                        }
                        if (Bank.withdraw(itemId, amount)) {
                            Condition.wait(() -> Inventory.stream().id(itemId).count(true) >= amount, 40, 300);
                            grabEquipmentFromBank.remove(itemId);
                        }
                    }
                }
            }
            if (grabEquipmentFromBank.isEmpty()) {
                Bank.close();
                Condition.wait(() -> !Bank.opened(), 30, 150);
                vars.gearWithdrawn = true;
            }
        }
    }
}
