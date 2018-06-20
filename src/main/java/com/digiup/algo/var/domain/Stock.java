package com.digiup.algo.var.domain;

import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public final class Stock {
    public Stock (Ticker ticker) {
        this.ticker = ticker;
    }

    public StockPrice add(StockPrice stockPrice) {
        prices.add(stockPrice);
        return stockPrice;
    }

    public List<StockPrice> getPrices() {
        return prices.stream().collect(Collectors.toList());
    }

    @Getter
    private final Ticker ticker;

    private NavigableSet<StockPrice> prices = new TreeSet<>(Comparator.comparing(StockPrice::getDate));
}
