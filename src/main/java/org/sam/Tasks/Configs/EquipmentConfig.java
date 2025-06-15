package org.sam.Tasks.Configs;

import org.powbot.api.rt4.stream.item.EquipmentItemStream;
import org.powbot.api.rt4.stream.item.InventoryItemStream;
import org.powbot.api.rt4.stream.item.ItemStream;

import java.util.List;

public class EquipmentConfig {
    private final ItemStream<EquipmentItemStream> equipmentStream;

    public EquipmentConfig(ItemStream<EquipmentItemStream> equipmentStream) {
        this.equipmentStream = equipmentStream;
    }

    public ItemStream<EquipmentItemStream> equipmentStream() {
        return equipmentStream;
    }
}
