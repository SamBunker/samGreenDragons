package org.sam;

import org.powbot.api.rt4.*;
import org.powbot.mobile.rlib.generated.RItemDefinition;
import org.powbot.mobile.rscache.loader.ItemLoader;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;

import java.util.*;

public class Functions {

    public Functions() {
        super();
    }
    static InventoryConfig inventoryConfig;
    static EquipmentConfig equipmentConfig;

    public static boolean hasItem(String name) {
        return Inventory.stream().nameContains(name).isNotEmpty() ||
                Equipment.stream().nameContains(name).isNotEmpty();
    }

    public static Item getFirstInventoryItemByID(int id) {
        return Inventory.stream().id(id).first();
    }

    public static boolean inventoryMatchesConfig(InventoryConfig inventoryConfig) {
        Map<Integer, Integer> currentInventory = new HashMap<>(); // or LinkedHashMap if you want to preserve order

        Inventory.stream().forEach(item -> {
            int id = item.id();
            int amount = item.getStack();
            currentInventory.put(id, amount);
        });

        return currentInventory.equals(inventoryConfig.getInventoryStream());
    }

    public static boolean equipmentMatchesConfig(EquipmentConfig equipmentConfig) {

        Set<Integer> currentEquipment = new HashSet<>();
        Equipment.stream().forEach(item -> {
            currentEquipment.add(item.id());
        });
        return currentEquipment.equals(equipmentConfig.equipmentStream());
    }


    // CHANGE THIS
    public static Set<Integer> getEquipmentDifference(EquipmentConfig equipmentConfig) {
        List<String> ammoTypes = Arrays.asList("arrow", "bolt", "javelin", "dart", "brutal");
        Set<Integer> difference = new HashSet<>();

        Map<Integer, Integer> currentEquipment = new HashMap<>();
        Equipment.stream().forEach(item -> {
            int id = item.id();
            int amount = item.getStack();
            currentEquipment.put(id, currentEquipment.getOrDefault(id, 0) + amount);
        });

        for (Integer expectedId : equipmentConfig.equipmentStream()) {
            int actualAmount = currentEquipment.getOrDefault(expectedId, 0);
            RItemDefinition itemDef = ItemLoader.lookup(expectedId);
            String name = itemDef != null ? itemDef.name().toLowerCase() : "";
            boolean isAmmo = ammoTypes.stream().anyMatch(name::contains);

            if (isAmmo) {
                if (actualAmount == 0) {
                    difference.add(expectedId);
                }
            } else {
                difference.add(expectedId);
        }
        return difference;
    }

    public static Map<Integer, Integer> getInventoryDifference(InventoryConfig inventoryConfig) {
        Map<Integer, Integer> difference = new HashMap<>();
        Map<Integer, Integer> currentInventory = new HashMap<>();
        Inventory.stream().forEach(item -> {
            int id = item.id();
            int amount = item.getStack();
            currentInventory.put(id, currentInventory.getOrDefault(id, 0) + amount);
        });

        for (Map.Entry<Integer, Integer> entry: inventoryConfig.getInventoryStream().entrySet()) {
            int id = entry.getKey();
            int expectedAmount = entry.getValue();
            int actualAmount = currentInventory.getOrDefault(id, 0);

            if (actualAmount < expectedAmount) {
                difference.put(id, expectedAmount - actualAmount);
            }
        }
        return difference;
    }
}