package org.sam.Utils;

import org.powbot.api.rt4.*;
import org.sam.Configs.EquipmentConfig;
import org.sam.Configs.InventoryConfig;

import java.util.*;

public class Functions {

    public Functions() {
        super();
    }

    public static InventoryConfig inventoryConfig;
    public static EquipmentConfig equipmentConfig;

    public static boolean hasItemInInventory(String name) {
        return Inventory.stream().nameContains(name).isNotEmpty() ||
                Equipment.stream().nameContains(name).isNotEmpty();
    }

    public static boolean hasEquipped(int id) {
        return Equipment.stream().id(id).isNotEmpty();
    }

    public static int[] missingEquipment(int[] items) {
        return Arrays.stream(items).filter(x -> !hasEquipped(x)).toArray();
    }

//    public static Map<Integer, Integer> missingInventory(Map<Integer, Integer> items) {
//        return Arrays.stream(items).filter(x -> !hasEquipped(x)).toArray();
//    }


    public static boolean inventoryMatchesConfig(InventoryConfig inventoryConfig) {
        Map<Integer, Integer> currentInventory = new HashMap<>();

        Inventory.stream().forEach(item -> {
            int id = item.id();
            int amount = item.getStack();
            currentInventory.put(id, currentInventory.getOrDefault(id, 0) + amount);
        });

        Map<Integer, Integer> missingItems = new HashMap<>(inventoryConfig.getInventoryStream());
        Iterator<Map.Entry<Integer, Integer>> iterator = missingItems.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> entry = iterator.next();
            int id = entry.getKey();
            int expectedAmount = entry.getValue();
            int actualAmount = currentInventory.getOrDefault(id, 0);

            if (actualAmount >= expectedAmount) {
                iterator.remove();
            }
        }
        return missingItems.isEmpty();
    }

//    public static boolean equipmentMatchesConfig(EquipmentConfig equipmentConfig) {
//        HashMap<Integer, Integer> currentEquipment = new HashMap<>();
//        Equipment.stream().forEach(item -> {
//            currentEquipment.put(item.id(), item.stackSize());
//        });
//        Map<Integer, Integer> missingItems = new HashMap<>(equipmentConfig.equipmentStream());
//        Iterator<Map.Entry<Integer, Integer>> iterator = missingItems.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, Integer> entry = iterator.next();
//            int id = entry.getKey();
//            int expectedAmount = entry.getValue();
//            int actualAmount = currentEquipment.getOrDefault(id, 0);
//
//            if (actualAmount >= expectedAmount) {
//                iterator.remove();
//            }
//        }
//        return missingItems.isEmpty();
//    }


    // CHANGE THIS
//    public static Map<Integer, Integer> getEquipmentDifference(EquipmentConfig config) {
//        int[] expectedEquipment = config.equipmentStream();
//        Map<Integer, Integer> differences = new HashMap<>();
//        List<String> ammoTypes = Arrays.asList("arrow", "bolt", "javelin", "dart", "brutal");
//
//        Map<Integer, Integer> currentEquipment = new HashMap<>();
//        Equipment.stream().forEach(item -> {
//            int id = item.id();
//            int amount = item.getStack();
//            currentEquipment.put(id, currentEquipment.getOrDefault(id, 0) + amount);
//        });
//
//         //Calculating Differences
//        for (Map.Entry<Integer, Integer> entry : expectedEquipment.entrySet()) {
//            int expectedId = entry.getKey();
//
//            RItemDefinition itemDef = ItemLoader.lookup(expectedId);
//            String name = itemDef != null ? itemDef.name().toLowerCase() : "";
//            boolean isAmmo = ammoTypes.stream().anyMatch(name::contains);
//            int actualAmount = currentEquipment.getOrDefault(expectedId, 0);
//            int count = isAmmo ? config.getQuiverStackSize() : 1;
//
//            if (actualAmount < count) {
//                differences.put(expectedId, count - actualAmount);
//            }
//        }
//        return differences;
//    }

//    public static Set<Integer> getEquipmentDifference(EquipmentConfig equipmentConfig) {
//        List<String> ammoTypes = Arrays.asList("arrow", "bolt", "javelin", "dart", "brutal");
//        Set<Integer> difference = new HashSet<>();
//
//        Map<Integer, Integer> currentEquipment = new HashMap<>();
//        Equipment.stream().forEach(item -> {
//            int id = item.id();
//            int amount = item.getStack();
//            currentEquipment.put(id, currentEquipment.getOrDefault(id, 0) + amount);
//        });
//
//        for (Integer expectedId : equipmentConfig.equipmentStream()) {
//            RItemDefinition itemDef = ItemLoader.lookup(expectedId);
//            String name = itemDef != null ? itemDef.name().toLowerCase() : "";
//            boolean isAmmo = ammoTypes.stream().anyMatch(name::contains);
//
//            int actualAmount = currentEquipment.getOrDefault(expectedId, 0);
//
//            if (isAmmo) {
//                int startingStack = equipmentConfig.getQuiverStackSize();
//                if (actualAmount == 0) {
//                    difference.add(expectedId);
//                }
//            } else {
//                difference.add(expectedId);
//            }
//            return difference;
//        }
//    }

    public static Map<Integer, Integer> getInventoryDifference(InventoryConfig inventoryConfig) {
        Map<Integer, Integer> difference = new HashMap<>();
        Map<Integer, Integer> currentInventory = new HashMap<>();
        Inventory.stream().forEach(item -> {
            int id = item.id();
            int amount = item.getStack();
            currentInventory.put(id, currentInventory.getOrDefault(id, 0) + amount);
        });

        for (Map.Entry<Integer, Integer> entry : inventoryConfig.getInventoryStream().entrySet()) {
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