package com.digiup.algo.var.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.digiup.algo.var.domain.Stock;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class StockManager extends AbstractLoggingActor {

    public StockManager(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ResetRequest.class, this::onResetRequest)
                .match(StockQuery.class, this::onQuery)
                .build();
    }

    public static class StockQuery {
        public StockQuery(final String ticker) { this.ticker = ticker; }
        final String ticker;
    }

    public static class ResetRequest {
        public ResetRequest(final String ticker) { this.ticker = ticker; }
        final String ticker;
    }

    public static Props props(String ticker) {
        return Props.create(StockManager.class, ticker);
    }

    private void onResetRequest(ResetRequest request) {
        if (!request.ticker.equalsIgnoreCase(this.ticker)) {
            log().warning("Ignoring request for " + request.ticker + " as this actor is not responsible for that ticker");
            return;
        }

        this.stockOptional = Optional.empty();
    }

    private void onQuery(StockQuery query) {
        if (!query.ticker.equalsIgnoreCase(this.ticker)) {
            log().warning("Ignoring request for " + query.ticker + " as this actor is not responsible for that ticker");
            return;
        }

        final Stock stock = stockOptional.orElseGet(() -> {
            log().debug("requesting load of " + query.ticker);
            final Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
            final ActorRef actorRef = getContext().actorOf(StockLoadingManager.props("C:/Users/natha/workspace/var/src/test/resources", query.ticker));
            final Future<Object> future = Patterns.ask(actorRef, new StockLoadingManager.LoadRequest(this.ticker), timeout);
            try {
                final Stock result = (Stock) Await.result(future, timeout.duration());
                stockOptional = Optional.of(result);
                return result;
            } catch (Exception e) { e.printStackTrace(); return null; }
        });

        getSender().tell(stock, getSender());
    }

    private Optional<Stock> stockOptional = Optional.empty();
    private final String ticker;
}
