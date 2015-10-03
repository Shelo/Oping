package com.shelodev.oping2.structure;

import java.util.ArrayList;

public class Leaf
{
    private ArrayList<String> values;
    private String name;

    public Leaf(String name, String... values)
    {
        this(name);

        addValues(values);
    }

    public Leaf(String name)
    {
        this.name = name;
        values = new ArrayList<>();
    }

    public Leaf()
    {
        this(null);
    }

    public void addValues(String... values)
    {
        for (String value : values)
            addValue(value);
    }

    public void addValue(String value)
    {
        values.add(value);
    }

    public int getInt(int index)
    {
        return Integer.parseInt(values.get(index));
    }

    public float getFloat(int index)
    {
        return Float.parseFloat(values.get(index));
    }

    public double getDouble(int index)
    {
        return Double.parseDouble(values.get(index));
    }

    public boolean getBoolean(int index)
    {
        return Boolean.parseBoolean(values.get(index));
    }

    public String getString(int index)
    {
        return values.get(index);
    }

    /**
     * Sets the value to the given index.
     *
     * @param index     index of the value.
     * @param value     new value.
     * @return          this leaf for chaining.
     */
    public Leaf setValue(int index, String value)
    {
        values.set(index, value);

        return this;
    }

    /**
     * Sets the value to the given index.
     *
     * @param index     index of the value.
     * @param value     new value.
     * @return          this leaf for chaining.
     */
    public Leaf setValue(int index, int value)
    {
        return setValue(index, String.valueOf(value));
    }

    /**
     * Sets the value to the given index.
     *
     * @param index     index of the value.
     * @param value     new value.
     * @return          this leaf for chaining.
     */
    public Leaf setValue(int index, float value)
    {
        return setValue(index, String.valueOf(value));
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
            result.append("    ");
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

    public int size()
    {
        return values.size();
    }
}
