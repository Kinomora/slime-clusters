package com.rayferric.slimeclusters;

import com.kinomora.utils.PrintUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.util.Date;
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
    public static void main(@NotNull String[] args) throws IOException {
        //Arg variables
        int minimumClusterSize, chunkRange;

        if(args.length == 0){
            minimumClusterSize = Integer.parseInt("23");
            chunkRange = Integer.parseInt("1024");
        } else if (args.length == 2) {
            minimumClusterSize = Integer.parseInt(args[0]);
            chunkRange = Integer.parseInt(args[1]);
        } else {
            minimumClusterSize = Integer.parseInt("23");
            chunkRange = Integer.parseInt("1024");
        }

        //Create a clock object for the println code --Kino
        Clock clock = Clock.systemDefaultZone();

        //Change print method to my custom date/time one --Kino
        //System.setOut(new PrintUtils(System.out, clock));


        BufferedWriter writer = new BufferedWriter(new FileWriter("clusters.txt"));

        // Maps world seeds to lists of clusters:
        SearchMapper mapper = new SearchMapper(chunkRange, minimumClusterSize);

        // Get a stream of seeds to test:
        LongStream seeds = LongStream.range(0L, 1L << 48);

        // Map them to cluster lists and flatten into a single stream:
        Stream<Cluster> clusters = seeds.mapToObj(mapper::run).flatMap(List::stream).parallel();


        // Process and print:
        clusters.forEach(cluster -> {
            try {
                System.out.println(cluster);
                writer.write(cluster.toString());
                writer.newLine();
                writer.flush();
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        });

        writer.close();
    }
}
