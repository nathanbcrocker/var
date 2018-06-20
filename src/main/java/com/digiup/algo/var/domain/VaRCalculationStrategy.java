package com.digiup.algo.var.domain;

import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.Function;

public final class VaRCalculationStrategy {

    enum Strategy {
        OpeningPriceVaRCalculationStrategy(openingPriceFunction),
        ClosingPriceVaRCalculationStrategy(closingPriceFunction),
        LowPriceVaRCalculationStrategy(lowPriceFunction),
        HighPriceVaRCalculationStrategy(highPriceFunction);

        Strategy(Function<StockPrice, Function<StockPrice, Double>> func) {
            this.risker = func;
        }

        private final Function<StockPrice, Function<StockPrice, Double>> risker;
    }

    public Optional<ValueAtRisk> calculate(final Stock stock) {
        return calculate(stock, Strategy.ClosingPriceVaRCalculationStrategy);
    }

    public Optional<ValueAtRisk> calculate(final Stock stock,  Strategy strategy) {
        List<StockPrice> prices = stock.getPrices();
        if (prices.size() == 0) return Optional.empty();

        final Vector<ValueAtRiskConstituent> intermediate = new Vector<>();
        StockPrice previous = prices.remove(0);
        for (StockPrice current : prices) {
            intermediate.add(new ValueAtRiskConstituent(current.getDate(), strategy.risker.apply(previous).apply(current)));
            previous = current;
        }

        return Optional.of(new ValueAtRisk(stock, intermediate));
    }

    private static Function<StockPrice, Function<StockPrice, Double>> openingPriceFunction = p -> c -> (c.getOpeningPrice() - p.getClosingPrice()) / p.getClosingPrice();

    private static Function<StockPrice, Function<StockPrice, Double>> closingPriceFunction = p -> c -> (c.getClosingPrice() - p.getClosingPrice()) / p.getClosingPrice();

    private static Function<StockPrice, Function<StockPrice, Double>> lowPriceFunction = p -> c -> (c.getLowPrice() - p.getClosingPrice()) / p.getClosingPrice();

    private static Function<StockPrice, Function<StockPrice, Double>> highPriceFunction = p -> c -> (c.getHighPrice() - p.getClosingPrice()) / p.getClosingPrice();
}
