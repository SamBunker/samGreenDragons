package org.sam;

import org.powbot.api.rt4.*;
import org.powbot.api.rt4.stream.item.EquipmentItemStream;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.item.ItemStream;
import org.sam.Tasks.Configs.EquipmentConfig;
import org.sam.Tasks.Configs.InventoryConfig;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    public Functions() {
        super();
    }
    InventoryConfig inventoryConfig;
    EquipmentConfig equipmentConfig;

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

    public List<Item> getInventoryDifference() {
        List<Item> difference = new ArrayList<>();
        ItemStream<InventoryItemStream> currentInventory = Inventory.stream();
        currentInventory.forEach(item -> {
            int id = item.id();
            int amount = item.
            if (!inventoryConfig.getInventoryStream().id(id).contains()) {
                if
                difference.add(item);
            }
        });
        return difference;
    }
}