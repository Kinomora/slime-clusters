package com.rayferric.slimeclusters;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Maps world seeds to lists of clusters.
 */
public class SearchMapper {
    /**
     * Constructs new cluster search mapper.
     *
     * @param extent  the extents of the searched region in chunks
     * @param minSize minimum amount of chunks in a cluster
     */
    public SearchMapper(int extent, int minSize) {
        this.extent = extent;
        this.minSize = minSize;
    }

    /**
     * Maps single world seed to a list of clusters.
     *
     * @param worldSeed world seed
     *
     * @return list of clusters
     */
    public List<Cluster> run(long worldSeed) {
        //Print the current world seed, eventually attach a sps calculator here --Kino
        //printCurrentSeed(worldSeed);

        List<Cluster> clusters = new ArrayList<>();

        BitField cache = new BitField((extent * 2L) * (extent * 2L));

        for(int chunkX = -extent; chunkX < extent; chunkX++) {
            for(int chunkZ = -extent; chunkZ < extent; chunkZ++) {
                int size = findClusters(cache, worldSeed, chunkX, chunkZ);
                if(size >= minSize)
                    clusters.add(new Cluster(worldSeed, chunkX, chunkZ, size));
            }
        }

        return clusters;
    }

    private final int extent, minSize;

    private int findClusters(@NotNull BitField cache, long worldSeed, int chunkX, int chunkZ) {

        // Map two-dimensional position to a one-dimensional index:
        long cacheIdx = (long)(chunkX + extent) * (extent * 2) + (chunkZ + extent);

        // We don't want to process chunks multiple times:
        if(cache.get(cacheIdx))
            return 0;

        cache.set(cacheIdx, true);

        if(!checkSlimeChunk(worldSeed, chunkX, chunkZ))
            return 0;

        int count = 1;

        // Process neighboring chunks:
        if(chunkX + 1 < extent)
            count += findClusters(cache, worldSeed, chunkX + 1, chunkZ);
        if(chunkX - 1 >= -extent)
            count += findClusters(cache, worldSeed, chunkX - 1, chunkZ);
        if(chunkZ + 1 < extent)
            count += findClusters(cache, worldSeed, chunkX, chunkZ + 1);
        if(chunkZ - 1 >= -extent)
            count += findClusters(cache, worldSeed, chunkX, chunkZ - 1);

        return count;
    }

    // Name is self-explanatory. Checks whether a given chunk has the ability to spawn slimes.
    private boolean checkSlimeChunk(long worldSeed, int chunkX, int chunkZ) {
        worldSeed += chunkX * chunkX * 0x4C1906;
        worldSeed += chunkX * 0x5AC0DB;
        worldSeed += chunkZ * chunkZ * 0x4307A7L;
        worldSeed += chunkZ * 0x5F24F;
        worldSeed ^= 0x3AD8025FL;

        Random rand = new Random(worldSeed);
        return rand.nextInt(10) == 0;
    }

    //Placeholder method for printing current working seed, causes memory leak --Kino
    private void printCurrentSeed(long worldSeed){
        System.out.print("Checking seed: " + worldSeed);
    }
}
