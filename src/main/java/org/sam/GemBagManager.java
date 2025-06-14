package org.sam;

import java.util.HashMap;
import java.util.Map;

public class GemBagManager {
    private final Map<String, Integer> gemCounts = new HashMap<>();

    public void updateGemCount(String gemName, int count) {
        gemCounts.put(gemName, count);
    }

    public int getCount(String gemName) {
        return gemCounts.getOrDefault(gemName, 0);
    }

    public boolean anyGemFull() {
        return gemCounts.values().stream().anyMatch(count -> count >= 60);
    }

    public void reset() {
        gemCounts.clear();
    }
}
