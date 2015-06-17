package com.shelodev.oping;

public abstract class Node
{
    private String name;
    private OpingParser.NodeType type;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean hasName(String name)
    {
        return this.name.equals(name);
    }

    public boolean isBranch()
    {
        return type == OpingParser.NodeType.BRANCH;
    }

    public boolean isLeaf()
    {
        return type == OpingParser.NodeType.LEAF;
    }

    public abstract OpingParser.NodeType getType();
}
