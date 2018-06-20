package com.digiup.algo.var.loader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DelimitedFileLoader<T> {
    public final static String comma = ",";
    public final static String pipe = "|";

    public DelimitedFileLoader(EntityMapper<T> mapper) {
        this(mapper, comma);
    }

    public DelimitedFileLoader(EntityMapper<T> mapper, String delimiter) {
        this(mapper, delimiter, 0, Long.MAX_VALUE);
    }

    public DelimitedFileLoader(EntityMapper<T> mapper, int skipNumber, long limitNumber) {
        this(mapper, comma, skipNumber, limitNumber);
    }


    public DelimitedFileLoader(EntityMapper<T> mapper, String delimiter, int skipNumber, long limitNumber) {
        this.mapper = mapper;
        this.delimiter = delimiter;
        this.skipNumber = skipNumber;
        this.limitNumber = limitNumber;
    }

    public Stream<T> stream(String path) {
        try { return Files.lines(Paths.get(path))
                .skip(skipNumber)
                .limit(limitNumber)
                .map(e -> mapper.map(e.split(delimiter)));
        }
        catch (Exception ex) { ex.printStackTrace(); return Stream.empty(); }
    }

    public List<T> list(String path) {
        return stream(path).collect(Collectors.toList());
    }

    private final String delimiter;
    private final EntityMapper<T> mapper;
    private final int skipNumber;
    private final long limitNumber;
}
