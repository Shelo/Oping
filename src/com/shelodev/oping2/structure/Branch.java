package com.shelodev.oping2.structure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Branch
{
    private ArrayList<Leaf> leafs;
    private ArrayList<Branch> branches;
    private String[] data;

    public Branch()
    {
        leafs = new ArrayList<>();
        branches = new ArrayList<>();
    }

    public void setName(String name)
    {
        if (data == null)
        {
            data = new String[] { name };
        }
        else
        {
            if (data.length == 1)
            {
                data[0] = name;
            }
            else
            {
                data[1] = name;
            }
        }
    }

    public void setNamespace(String namespace)
    {
        if (data == null)
        {
            data = new String[] { namespace, null };
        }
        else
        {
            if (data.length == 1)
            {
                String[] copy = data;
                data = new String[] { namespace, copy[0] };
            }
            else
            {
                data[0] = namespace;
            }
        }
    }

    public void setData(String namespace, String name)
    {
        data = new String[] { namespace, name };
    }

    public String getName()
    {
        if (data.length == 1)
        {
            return data[0];
        }

        return data[1];
    }

    public String getNamespace()
    {
        if (data.length == 2)
        {
            return data[0];
        }

        return null;
    }

    public void addBranch(Branch branch)
    {
        branches.add(branch);
    }

    public void addLeaf(Leaf leaf)
    {
        leafs.add(leaf);
    }

    public ArrayList<Branch> getBranches()
    {
        return branches;
    }

    public ArrayList<Leaf> getLeafs()
    {
        return leafs;
    }

    public String toString(int level)
    {
        StringBuilder result = new StringBuilder();
        result.append("\n");

        for (int i = 0; i < level; i++)
        {
            result.append("   ");
        }

        if (data.length == 1)
        {
            result.append("+ ").append(data[0]).append("\n");
        }
        else
        {
            result.append("+ ").append(data[0]).append(":").append(data[1]).append("\n");
        }

        for (Leaf leaf : leafs)
        {
            result.append(leaf.toString(level + 1));
        }

        for (Branch branch : branches)
        {
            result.append(branch.toString(level + 1));
        }

        return result.toString();
    }

    @Override
    public String toString()
    {
        return getNamespace() + ":" + getName();
    }
}
