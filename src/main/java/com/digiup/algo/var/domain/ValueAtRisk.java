package com.digiup.algo.var.domain;

import java.util.Map;
import java.util.Vector;

public class ValueAtRisk {
    public enum Level { NINTY_NINE, NINTY_FIVE }


    public ValueAtRisk(Stock stock, Vector<ValueAtRiskConstituent> values) {
        this.stock = stock;
        this.values = values;
    }

    public Stock getStock() {
        return stock;
    }

    public Vector<ValueAtRiskConstituent> getValues() {
        return values;
    }

    public ValueAtRiskConstituent getValue(Level level) {
        final var index = (int) Math.round(1 - levelMap.getOrDefault(level, 0.0)) * values.size();
        return values.get(index);
    }

    private static Map<Level, Double> levelMap = Map.of(Level.NINTY_FIVE, .95, Level.NINTY_NINE, .99);

    private Stock stock;

    private Vector<ValueAtRiskConstituent> values;
}
