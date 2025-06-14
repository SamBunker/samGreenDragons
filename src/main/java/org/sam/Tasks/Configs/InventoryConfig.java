package org.sam.Tasks.Configs;

import org.powbot.api.rt4.Inventory;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.item.ItemStream;


public class InventoryConfig {
    private final ItemStream<InventoryItemStream> inventoryStream;

    public InventoryConfig(ItemStream<InventoryItemStream> inventoryStream) {
        this.inventoryStream = inventoryStream;
    }

    public ItemStream<InventoryItemStream> getInventoryStream() {
        return inventoryStream;
    }
}
