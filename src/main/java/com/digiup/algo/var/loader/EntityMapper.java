package com.digiup.algo.var.loader;

@FunctionalInterface
public interface EntityMapper<T> {
    T map(String[] entry);
}
