package com.shelodev.oping;

import java.util.ArrayList;
import java.util.HashMap;

public class Branch extends Node
{
    private ArrayList<Branch> branches = new ArrayList<>();
    private HashMap<String, Leaf> leafs = new HashMap<>();

    private String name;

    /**
     * Creates a branch with a name but no id.
     *
     * @param name  name of this branch.
     */
    public Branch(String name)
    {
        setName(name);
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
            if (child.isBranch() && child.hasName(name))
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
            if (branch.isBranch() && branch.hasName(name))
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

    @Override
    public String toString()
    {
        return String.format("Branch %s with %d branches and %d leafs.", getName(), branches.size(), leafs.size());
    }

    public void printRecursive()
    {
        System.out.println("-> Leafs of " + getName() + " Branch.");
        for (String leafName : leafs.keySet())
        {
            System.out.println(leafs.get(leafName));
        }

        System.out.println("-> Branches of " + getName() + " Branch.");
        for (Branch branch : branches)
        {
            System.out.println(branch);
            branch.printRecursive();
        }

        System.out.println();
    }
}
