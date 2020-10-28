package com.rayferric.slimeclusters;

import java.util.Arrays;

/**
 * A bit field is able to store multiple boolean values while using 8 times less memory than a simple boolean array.
 */
public class BitField {
    /**
     * Construct a bit field filled with zeros.
     *
     * @param size the amount of bits for this field
     */
    public BitField(long size) {
        // Equivalent to: ceil(size / 64)
        int arraySize = (int)((size + (Long.SIZE - 1)) / Long.SIZE);

        assert arraySize > 0 : "Size must be in range [0, Integer.MAX_VALUE * Long.SIZE].";

        this.size = size;

        array = new long[arraySize];
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BitField other = (BitField)o;
        return Arrays.equals(array, other.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(long i = 0L; i < size; i++)
            builder.append(get(i) ? 1 : 0);

        return builder.toString();
    }

    /**
     * Returns a bit from this field.
     *
     * @param index bit index
     *
     * @return boolean state
     */
    public boolean get(long index) {
        assert (0 <= index && index < size) : ("Index must be in range [0, " + size + ").");

        int arrayIndex = (int)(index / Long.SIZE);
        int bitOffset = (int)(index % Long.SIZE);

        return ((array[arrayIndex] >>> bitOffset) & 1) == 1;
    }

    /**
     * Sets a bit in thi field.
     *
     * @param index bit index
     * @param state target state
     */
    public void set(long index, boolean state) {
        assert (0 <= index && index < size) : ("Index must be in range [0, " + size + ").");

        int arrayIndex = (int)(index / Long.SIZE);
        int bitOffset = (int)(index % Long.SIZE);

        if(state)
            array[arrayIndex] |= 1L << bitOffset;
        else
            array[arrayIndex] &= ~(1L << bitOffset);
    }

    private final long size;
    private final long[] array;
}
