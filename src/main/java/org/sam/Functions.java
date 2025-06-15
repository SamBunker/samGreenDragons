package org.sam;

import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.item.EquipmentItemStream;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.item.ItemStream;
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

    public boolean inventoryMatchesConfig() {
        ItemStream<InventoryItemStream> currentInventory = Inventory.stream();
        return currentInventory.equals(inventoryConfig.getInventoryStream());
    }

    public boolean equipmentMatchesConfig() {
        List<EquipmentItemStream> currentEquipment = List.of(Equipment.stream());
        return currentEquipment.equals(equipmentConfig.equipmentStream());
    }

    public static Map<Integer, Integer> getEquipmentDifference() {
        List<String> ammoTypes = Arrays.asList("arrow", "bolt", "javelin", "dart", "brutal");

        Map<Integer, Integer> difference = new HashMap<>();

        ItemStream<EquipmentItemStream> currentEquipment = Equipment.stream();

        EquipmentItemStream ammo = equipmentConfig.equipmentStream().filter(item -> ammoTypes.stream().anyMatch(type -> item.name().toLowerCase().contains(type)));
        if (ammo.first().valid()) {
            String configAmmoName = ammo.first().name();
            int configAmmoAmount = ammo.first().getStack();
            int configAmmoId = ammo.first().id();

            Item currentAmmo = Equipment.itemAt(Equipment.Slot.QUIVER);
            int currentAmmoAmount = currentAmmo.valid() ? currentAmmo.getStack() : 0;

            int missingAmmoAmount = configAmmoAmount - currentAmmoAmount;
            if (missingAmmoAmount > 0) {
                difference.put(configAmmoId, missingAmmoAmount);
            }
        }
        equipmentConfig.equipmentStream().forEach(item -> {

            String name = item.name();
            int id = item.id();

            if (Equipment.itemAt(Equipment.Slot.QUIVER).valid()) {
                int current = Equipment.itemAt(Equipment.Slot.QUIVER).getStack();
            }
//            if (!currentEquipment.id(id).contains()) {
//                ammoTypes.stream().anyMatch(type -> name.toLowerCase().contains(type))) {
//
//                }
//            }
        });
    }

    public static List<Item> getInventoryDifference() {
        List<Item> difference = new ArrayList<>();
        ItemStream<InventoryItemStream> currentInventory = Inventory.stream();

        inventoryConfig.getInventoryStream().forEach(item -> {
            int id = item.id();
            int amount = item.getStack();
            if (!currentInventory.id(id).contains()) {
                for (int i = 0; i < amount; i++) {
                    difference.add(item);
                }
            }
        });
        return difference;
    }
}