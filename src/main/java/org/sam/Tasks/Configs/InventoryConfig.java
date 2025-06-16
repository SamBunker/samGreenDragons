package org.sam.Tasks.Configs;

import java.util.Map;

public class InventoryConfig {
    private final Map<Integer, Integer> inventoryStream;
    public boolean gearEquipped = false;

    public InventoryConfig(Map<Integer, Integer> inventoryStream) {
        this.inventoryStream = inventoryStream;
    }

    public Map<Integer, Integer> getInventoryStream() {
        return inventoryStream;
    }
}