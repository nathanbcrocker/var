package com.digiup.algo.var.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Function;

import static com.digiup.algo.var.domain.VaRCalculationStrategy.Strategy.*;
import static org.junit.jupiter.api.Assertions.*;

class VaRCalculationStrategyTest {

    @Test
    void testDefaultVaR() {
        final var msft = stock();
        var p = new VaRCalculationStrategy();
        final var varDefaultOptional = p.calculate(msft);
        final var varClosingOptional = p.calculate(msft, ClosingPriceVaRCalculationStrategy);

        assertEquals((double) varDefaultOptional.get().getValue(ValueAtRisk.Level.NINTY_FIVE).getValue(), (double) varClosingOptional.get().getValue(ValueAtRisk.Level.NINTY_FIVE).getValue());
    }

    @Test
    void testCalculateOpeningVaR() {
        final var msft = stock();
        var p = new VaRCalculationStrategy();
        final var varOptional = p.calculate(msft, OpeningPriceVaRCalculationStrategy);
        assertTrue(varOptional.isPresent());
        assertEquals(0.003965893317469817, (double) varOptional.get().getValue(ValueAtRisk.Level.NINTY_FIVE).getValue());
    }

    @Test
    void testCalculateClosingVaR() {
        final var msft = stock();
        var p = new VaRCalculationStrategy();
        final var varOptional = p.calculate(msft, ClosingPriceVaRCalculationStrategy);
        assertTrue(varOptional.isPresent());
        assertEquals(0.009914733293674401, (double) varOptional.get().getValue(ValueAtRisk.Level.NINTY_FIVE).getValue());
    }

    @Test
    void testCalculateLowVaR() {
        final var msft = stock();
        var p = new VaRCalculationStrategy();
        final var varOptional = p.calculate(msft, LowPriceVaRCalculationStrategy);
        assertTrue(varOptional.isPresent());
        assertEquals(0.008030933967876287, (double) varOptional.get().getValue(ValueAtRisk.Level.NINTY_FIVE).getValue());
    }

    @Test
    void testCalculateHighVaR() {
        final var msft = stock();
        var p = new VaRCalculationStrategy();
        final var varOptional = p.calculate(msft, HighPriceVaRCalculationStrategy);
        assertTrue(varOptional.isPresent());
        assertEquals(-8.923259964307298E-5, (double) varOptional.get().getValue(ValueAtRisk.Level.NINTY_FIVE).getValue());
    }

    Stock stock() {
        final var msft = new Stock(new Ticker("MSFT"));
        addPrice.apply(msft).apply(new StockPrice(LocalDate.parse("2018-06-08"), 101.09, 101.63, 101.95, 100.54));
        addPrice.apply(msft).apply(new StockPrice(LocalDate.parse("2018-06-07"), 102.65, 102.69, 100.38, 100.88));
        addPrice.apply(msft).apply(new StockPrice(LocalDate.parse("2018-06-06"), 102.48, 102.6, 101.9, 102.49));
        addPrice.apply(msft).apply(new StockPrice(LocalDate.parse("2018-06-05"), 102.0,	102.33,	101.53,	102.19));
        addPrice.apply(msft).apply(new StockPrice(LocalDate.parse("2018-06-04"), 101.26, 101.86,	100.851, 101.67));
        addPrice.apply(msft).apply(new StockPrice(LocalDate.parse("2018-06-01"), 99.28,	100.86,	99.17, 100.79));
        return msft;
    }

    final Function<Stock, Function<StockPrice, Stock>> addPrice = s -> p -> { s.add(p); return s; };
}

