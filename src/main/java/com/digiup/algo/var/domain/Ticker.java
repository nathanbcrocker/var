package com.digiup.algo.var.domain;
import lombok.Getter;

public final class Ticker {

    public Ticker(String name) {
        this.name = name;
    }

    @Getter
    private final String name;
}
