package com.digiup.algo.var.actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.pattern.Patterns;

import java.util.HashMap;
import java.util.Map;

public class VaRManager extends AbstractLoggingActor {

    public static final class VaRCalculationRequest {
        public final String ticker;

        public VaRCalculationRequest(String ticker) {
            this.ticker = ticker;
        }
    }

    public static Props props() {
        return Props.create(VaRManager.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(VaRCalculationRequest.class, this::onVaRCalculationRequest)
                .build();
    }

    private void onVaRCalculationRequest(VaRCalculationRequest calculationRequest) {
        var ticker = calculationRequest.ticker;
        actorRefMap.computeIfAbsent(ticker, a -> getContext().actorOf(StockManager.props(ticker), "stock-" + ticker));

        var actorRef = actorRefMap.get(ticker);
        //Patterns.ask(actorRef, new StockManager.ValueAtRiskQuery(ticker), 5_000);
    }

    private final Map<String, ActorRef> actorRefMap = new HashMap<>();
}
