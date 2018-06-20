package com.digiup.algo.var.domain;

import java.time.LocalDate;

public class StockPrice {
    public StockPrice(LocalDate date, Double openingPrice, Double closingPrice,
                      Double highPrice, Double lowPrice) {
        this.date = date;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getOpeningPrice() {
        return openingPrice;
    }

    public Double getClosingPrice() {
        return closingPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    private final LocalDate date;

    private final Double openingPrice;

    private final Double closingPrice;

    private final Double highPrice;

    private final Double lowPrice;
}
