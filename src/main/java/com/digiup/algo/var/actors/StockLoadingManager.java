package com.digiup.algo.var.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.digiup.algo.var.domain.Stock;
import com.digiup.algo.var.domain.StockPrice;
import com.digiup.algo.var.domain.Ticker;
import com.digiup.algo.var.loader.DelimitedFileLoader;

public class StockLoadingManager extends AbstractLoggingActor {

    public StockLoadingManager(String path, String ticker) {
        this.path = path;
        this.ticker = ticker;
    }

    public static final class LoadRequest {
        public LoadRequest(String ticker) {
            this.ticker = ticker;
        }

        public final String ticker;
    }

    public static Props props(String path, String ticker) {
        return Props.create(StockLoadingManager.class, path, ticker);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(LoadRequest.class, this::onLoadRequest)
                .build();
    }

    @SuppressWarnings("unchecked")
    private void onLoadRequest(LoadRequest loadRequest) {
        var ticker = loadRequest.ticker;
        var l = new DelimitedFileLoader<StockPrice>(e ->
                new StockPrice(
                        LocalDate.parse(e[0], DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        Double.parseDouble(e[1]),
                        Double.parseDouble(e[2]),
                        Double.parseDouble(e[3]),
                        Double.parseDouble(e[4])
                ), 0, limit);
        final var stock = new Stock(new Ticker(ticker));
        l.stream(path + File.separator + ticker + ".txt").forEach(p -> stock.add(p));
        getSender().tell(stock, getSelf());
    }

    private String path;
    private final String ticker;

    private final long limit = 500;
}
