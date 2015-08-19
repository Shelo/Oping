package com.shelodev.oping2.structure;

import java.util.ArrayList;

public class Leaf
{
    private ArrayList<String> values;
    private String name;

    public Leaf(String name)
    {
        this.name = name;
        values = new ArrayList<>();
    }

    public Leaf()
    {
        this(null);
    }

    public void addValue(String value)
    {
        values.add(value);
    }

    public String getValue(int index)
    {
        return values.get(index);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getValues()
    {
        return values;
    }

    public String toString(int level)
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < level; i++)
        {
            result.append("   ");
        }

        ArrayList<String> strValues = new ArrayList<>();

        for (String value : values)
        {
            if (value.contains(","))
            {
                strValues.add("\"" + value + "\"");
            }
            else
            {
                strValues.add(value);
            }
        }

        result.append("- ").append(name).append(": ");
        result.append(String.join(", ", strValues));
        result.append("\n");

        return result.toString();
    }
}
