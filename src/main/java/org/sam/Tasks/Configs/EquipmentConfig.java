package org.sam.Tasks.Configs;

import java.util.Map;
import java.util.Set;


public class EquipmentConfig {
    private final int[] equipmentStream;
    private final int quiverStackSize;

    public EquipmentConfig(int[] equipmentStream, int quickerStackSize) {
        this.equipmentStream = equipmentStream;
        this.quiverStackSize = quickerStackSize;
    }

    public int[] equipmentStream() {
        return equipmentStream;
    }

    public int getQuiverStackSize() {
        return quiverStackSize;
    }

    public static int[] parseEquipmentIds(Object equipmentOption) {
        if (!(equipmentOption instanceof Map)) {
            return new int[0];
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> equipmentMap = (Map<String, Object>) equipmentOption;

        return equipmentMap.keySet()
                .stream()
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
