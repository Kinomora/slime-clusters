package com.rayferric.slimeclusters;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

//
// CUDA notes:
//
// 65536x65536 region takes 512 MiB of memory.
//
// Doing this with temporary per-cluster caches runs about 3 times slower.
//
// Testing one seed in this region on a single 2.66 GHz core takes up to 3 minutes and we have
// a whole lot of them to test, so maybe we can settle on a 512x512 region (32 kiB)?
//
// This will also fit nicely in the standard 48 kiB of per-thread GPU memory.
//
// The execution of a single thread should take about 0.01 s on 2.66 GHz in this scenario.
//
// Assuming that a single CUDA thread is about 10 times slower than that,
// running this routine can yield a maximum of 10240 seeds/s per-block performance.
//

public class Main {
    public static void main(@NotNull String[] args) {
        // Maps world seeds to lists of clusters:
        SearchMapper mapper = new SearchMapper(512, 20);

        // Get a stream of seeds to test:
        LongStream seeds = LongStream.rangeClosed(0, 100000);

        // Map them to cluster lists and flatten into a single stream:
        Stream<Cluster> clusters = seeds.mapToObj(mapper::run).flatMap(List::stream).parallel();

        // Process and print:
        clusters.forEach(System.out::println);
    }
}
