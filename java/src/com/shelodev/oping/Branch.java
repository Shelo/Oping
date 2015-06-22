package com.shelodev.oping;

import java.util.ArrayList;
import java.util.HashMap;

public class Branch extends Node
{
    private ArrayList<Branch> branches = new ArrayList<>();
    private HashMap<String, Leaf> leafs = new HashMap<>();

    private String namespace;

    /**
     * Creates a branch with a name but no namespace.
     *
     * @param name  name of this branch.
     */
    public Branch(String name)
    {
        setName(name);
    }

    /**
     * Creates a branch with a name and namespace.
     *
     * @param name  name of this branch.
     */
    public Branch(String namespace, String name)
    {
        this(name);

        this.namespace = namespace;
    }

    public void addBranch(Branch branch)
    {
        branches.add(branch);
    }

    public ArrayList<Branch> getBranches()
    {
        return branches;
    }

    /**
     * Returns the first branch with the given name.
     *
     * @param name  the name of the node.
     * @return      the first matching branch or null.
     */
    public Branch getBranch(String name)
    {
        for (Branch child : branches)
        {
            if (child.isBranch() && child.nameIs(name))
            {
                return child;
            }
        }

        return null;
    }

    /**
     * Returns all branches with the given name.
     *
     * @param name  the name of the branch.
     * @return      a list of branches.
     */
    public ArrayList<Branch> getBranches(String name)
    {
        ArrayList<Branch> branches = new ArrayList<>();

        for (Branch branch : branches)
        {
            if (branch.isBranch() && branch.nameIs(name))
            {
                branches.add(branch);
            }
        }

        return branches;
    }

    /**
     * Like getBranch, returns the first leaf with the name given.
     *
     * @param name  the name of the leaf.
     * @return      a leaf or null.
     */
    public Leaf getLeaf(String name)
    {
        return leafs.get(name);
    }

    public Branch getBranch(int index)
    {
        return branches.get(index);
    }

    public Branch getLastBranch()
    {
        if (!branches.isEmpty())
        {
            return branches.get(branches.size() - 1);
        }

        return null;
    }

    public void clear()
    {
        branches.clear();
        leafs.clear();
    }

    @Override
    public OpingParser.NodeType getType()
    {
        return OpingParser.NodeType.BRANCH;
    }

    public void addLeaf(Leaf leaf)
    {
        leafs.put(leaf.getName(), leaf);
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public boolean hasNamespace()
    {
        return namespace != null;
    }

    public boolean namespaceIs(String other)
    {
        return this.namespace.equals(other);
    }

    @Override
    public String toString()
    {
        if (hasNamespace())
        {
            return String.format("Branch %s:%s with %d branches and %d leafs.", getNamespace(), getName(),
                    branches.size(), leafs.size());
        }
        else
        {
            return String.format("Branch %s with %d branches and %d leafs.", getName(), branches.size(), leafs.size());
        }
    }

    public void printRecursive()
    {
        System.out.println(toString());
        System.out.println("-> Leafs:");
        for (String leafName : leafs.keySet())
        {
            System.out.println("   " + leafs.get(leafName));
        }

        System.out.println("-> Branches:");
        for (Branch branch : branches)
        {
            System.out.println("   " + branch);
            branch.printRecursive();
        }

        System.out.println();
    }
}
