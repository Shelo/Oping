package com.shelodev.oping2;

import java.util.Arrays;

public class IndexBuffer
{
    private static final int SPACE_STEP = 10;

    private int[] positions;
    private int[] lengths;
    private byte[] types;

    private int count;
    private int size;

    public IndexBuffer()
    {
        positions = new int[SPACE_STEP];
        lengths = new int[SPACE_STEP];
        types = new byte[SPACE_STEP];
    }

    public void setPosition(int index, int position)
    {
        ensureCapacity(index);
        positions[index] = position;
        fixCount(index);
    }

    public void setLength(int index, int length)
    {
        ensureCapacity(index);
        lengths[index] = length;
        fixCount(index);
    }

    public void setType(int index, byte type)
    {
        ensureCapacity(index);
        types[index] = type;
        fixCount(index);
    }

    private void fixCount(int index)
    {
        if (index >= count)
        {
            count = index + 1;
        }
    }

    public int getSize()
    {
        return size;
    }

    private void ensureCapacity(int capacity)
    {
        if (capacity >= size)
        {
            size += SPACE_STEP;

            positions = Arrays.copyOf(positions, size);
            lengths = Arrays.copyOf(lengths, size);
            types = Arrays.copyOf(types, size);
        }
    }

    public void debug(char[] data)
    {
        for (int i = 0; i < count; i++)
        {
            String subString = null;

            if (lengths[i] != 1)
            {
                subString = subString(data, positions[i], lengths[i]);
            }
            else
            {
                subString = Character.getName(data[positions[i]]);
            }

            System.out.println(String.format("[(%d) \tposition=%d, \tlength=%d, \ttype=%d]: \t%s",
                    i, positions[i], lengths[i], types[i], subString));
        }
    }

    public void close()
    {
        size = count;

        positions = Arrays.copyOf(positions, size);
        lengths = Arrays.copyOf(lengths, size);
        types = Arrays.copyOf(types, size);
    }

    private String subString(char[] data, int position, int length)
    {
        StringBuilder result = new StringBuilder();

        for (int i = position; i < position + length; i++)
        {
            result.append(data[i]);
        }

        return result.toString();
    }

    public int getCount()
    {
        return count;
    }

    public byte getType(int index)
    {
        return types[index];
    }

    public int getPosition(int index)
    {
        return positions[index];
    }

    public int getLength(int index)
    {
        return lengths[index];
    }
}
