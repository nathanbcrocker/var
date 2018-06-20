package com.digiup.algo.var.actors;

import akka.actor.AbstractLoggingActor;
import com.digiup.algo.var.domain.ValueAtRisk;
import com.digiup.algo.var.domain.ValueAtRiskConstituent;

import java.util.Vector;

public class PriceManager extends AbstractLoggingActor {
    Vector<ValueAtRiskConstituent> createVector() { return null; }
    ValueAtRiskConstituent varByLevel(ValueAtRisk.Level level) { return  null; }

    @Override
    public Receive createReceive() {
        return null;
    }
}
