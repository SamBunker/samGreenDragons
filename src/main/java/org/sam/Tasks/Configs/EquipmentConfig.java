package org.sam.Tasks.Configs;

import org.powbot.api.rt4.stream.item.EquipmentItemStream;
import org.powbot.api.rt4.stream.item.InventoryItemStream;

import java.util.List;

public class EquipmentConfig {
    private final List<EquipmentItemStream> equipmentStream;

    public EquipmentConfig(List equipmentStream) {
        this.equipmentStream = equipmentStream;
    }

    public List<EquipmentItemStream> equipmentStream() {
        return equipmentStream;
    }
}
