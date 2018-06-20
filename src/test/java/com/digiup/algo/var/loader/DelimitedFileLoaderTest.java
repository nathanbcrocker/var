package com.digiup.algo.var.loader;

import com.digiup.algo.var.domain.StockPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DelimitedFileLoaderTest {

    final String path = "./src/test/resources/MSFT.txt";
    final long limit = 500;
    private DelimitedFileLoader<StockPrice> l;


    @BeforeEach
    @SuppressWarnings("unchecked")
    void init() {
        l = new DelimitedFileLoader(e ->
                new StockPrice(
                        LocalDate.parse(e[0], DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        Double.parseDouble(e[1]),
                        Double.parseDouble(e[2]),
                        Double.parseDouble(e[3]),
                        Double.parseDouble(e[4])
                        ), 0, limit);
    }

    @Test
    void testStream() {
        var stockPriceStream = l.stream(path);
        assertEquals(limit, stockPriceStream.count());
    }

    @Test
    void testList() {
        var stockPriceList = l.list(path);
        StockPrice oldest = stockPriceList.stream().skip(limit - 1).findAny().get();
        StockPrice newest = stockPriceList.stream().limit(1).findAny().get();
        assertEquals(limit, stockPriceList.size());
        assertEquals(LocalDate.parse("2016-06-14"), oldest.getDate());
        assertEquals(LocalDate.parse("2018-06-07"), newest.getDate());
    }

    @Test
    void testNonExistentFile() {
        List<StockPrice> stockPriceList = l.list("FILE_NOT_FOUND");
        assertEquals(0, stockPriceList.size());
    }
}