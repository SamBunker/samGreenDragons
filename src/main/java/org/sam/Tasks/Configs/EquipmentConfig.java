package org.sam.Tasks.Configs;

import java.util.LinkedHashMap;
import java.util.Set;


public class EquipmentConfig {
    private final Set<Integer> equipmentStream;

    public EquipmentConfig(Set<Integer> equipmentStream) {
        this.equipmentStream = equipmentStream;
    }

    public Set<Integer> equipmentStream() {
        return equipmentStream;
    }
}
