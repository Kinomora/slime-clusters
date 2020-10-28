package com.rayferric.slimeclusters;

/**
 * Encapsulates information about a single cluster of chunks, immutable type.
 */
public class Cluster {
    /**
     * Constructs a cluster.
     *
     * @param worldSeed world seed
     * @param chunkX    chunk X position
     * @param chunkZ    chunk Z position
     * @param size      size of that cluster
     */
    public Cluster(long worldSeed, int chunkX, int chunkZ, int size) {
        this.worldSeed = worldSeed;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("Cluster of %s chunks at %s, %s (seed: %s).", size, chunkX * 16, chunkZ * 16, worldSeed);
    }

    /**
     * Returns the world seed.
     *
     * @return world seed
     */
    public long getWorldSeed() {
        return worldSeed;
    }

    /**
     * Returns chunk position on the X axis.
     *
     * @return X chunk position
     */
    public int getChunkX() {
        return chunkX;
    }

    /**
     * Returns chunk position on the Z axis.
     *
     * @return Z chunk position
     */
    public int getChunkZ() {
        return chunkZ;
    }

    /**
     * Returns the amount of chunks in this cluster.
     *
     * @return size
     */
    public int getSize() {
        return size;
    }

    private final long worldSeed;
    private final int chunkX, chunkZ, size;
}
