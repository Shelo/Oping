package com.shelodev.oping;

import java.util.ArrayList;

public class Leaf extends Node
{
    private ArrayList<String> values = new ArrayList<>();

    public Leaf(String name)
    {
        setName(name);
    }

    public void addValue(String value)
    {
        values.add(value);
    }

    public void insertValue(int index, String value)
    {
        values.add(index, value);
    }

    public void removeValue(int index)
    {
        values.remove(index);
    }

    public String getValue(int index)
    {
        return values.get(index);
    }

    public void clear()
    {
        values.clear();
    }

    @Override
    public OpingParser.NodeType getType()
    {
        return OpingParser.NodeType.LEAF;
    }

    @Override
    public String toString()
    {
        return String.format("Leaf %s with values: %s", getName(), values.toString());
    }
}
