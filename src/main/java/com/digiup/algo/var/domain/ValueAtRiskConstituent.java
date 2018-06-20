package com.digiup.algo.var.domain;

import java.time.LocalDate;

public class ValueAtRiskConstituent {
    public ValueAtRiskConstituent(LocalDate date, Double value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    private final LocalDate date;

    private final Double value;
}
