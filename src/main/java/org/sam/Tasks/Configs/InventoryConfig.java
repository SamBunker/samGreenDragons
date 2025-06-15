package org.sam.Tasks.Configs;

import java.util.Collections;
import java.util.Map;

public class InventoryConfig {
    private final Map<Integer, Integer> inventoryStream;

    public InventoryConfig(Map<Integer, Integer> inventoryStream) {
        this.inventoryStream = inventoryStream;
    }

    public Map<Integer, Integer> getInventoryStream() {
        return inventoryStream;
    }
}