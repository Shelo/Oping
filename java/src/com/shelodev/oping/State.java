package com.shelodev.oping;

import com.shelodev.oping.structure.Branch;
import com.shelodev.oping.structure.Leaf;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Intended to be a bundle of states used by the {@link OpingParser}.
 */
public class State
{
    private OpingParser.Indentation indentationType = OpingParser.Indentation.NONE;
    private OpingParser.NodeType previousNodeType = OpingParser.NodeType.ROOT;
    private int previousIndentationLevel = 0;
    private Branch branches;
    private int indentationWidth = -1;
    private int lineNumber = 0;

    private boolean recycle;
    private LinkedBlockingQueue<Branch> branchesPool;
    private LinkedBlockingQueue<Leaf> leafsPool;

    public void setIndentationWidth(int indentationWidth)
    {
        this.indentationWidth = indentationWidth;
    }

    public void setIndentationType(OpingParser.Indentation indentationType)
    {
        this.indentationType = indentationType;
    }

    public void incrementLineNumber()
    {
        this.lineNumber++;
    }

    public void setPreviousIndentationLevel(int previousIndentationLevel)
    {
        this.previousIndentationLevel = previousIndentationLevel;
    }

    public void setPreviousNodeType(OpingParser.NodeType previousNodeType)
    {
        this.previousNodeType = previousNodeType;
    }

    public OpingParser.Indentation getIndentationType()
    {
        return indentationType;
    }

    public int getIndentationWidth()
    {
        return indentationWidth;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public int getPreviousIndentationLevel()
    {
        return previousIndentationLevel;
    }

    public OpingParser.NodeType getPreviousNodeType()
    {
        return previousNodeType;
    }

    public void setBranchesRoot(Branch branches)
    {
        this.branches = branches;
    }

    public Branch getRoot()
    {
        return branches;
    }

    public void enableRecycling()
    {
        this.recycle = true;
        leafsPool = new LinkedBlockingQueue<>();
        branchesPool = new LinkedBlockingQueue<>();
    }

    public boolean isRecycling()
    {
        return recycle;
    }

    /**
     * Returns a leaf from the pool with a given name, always empty.
     *
     * @param name  the name for the new leaf.
     * @return      the empty leaf.
     */
    public Leaf takeLeaf(String name)
    {
        if (leafsPool.isEmpty())
        {
            return new Leaf(name);
        }
        else
        {
            Leaf leaf = leafsPool.poll();
            leaf.setName(name);
            leaf.clear();
            return leaf;
        }
    }

    /**
     * Returns a branch from the pool with a given name, always empty.
     *
     * @param namespace a namespace for the branch
     * @param name      a name for the branch
     * @return          the empty branch.
     */
    public Branch takeBranch(String namespace, String name)
    {
        if (branchesPool.isEmpty())
        {
            return new Branch(namespace, name);
        }
        else
        {
            Branch branch = branchesPool.poll();
            branch.setName(name);
            branch.setNamespace(namespace);
            branch.clear();
            return branch;
        }
    }

    public void putBranch(Branch branch)
    {
        try
        {
            branchesPool.put(branch);
        }
        catch (InterruptedException e)
        {
            // This will never happen.
        }
    }

    public void putLeaf(Leaf leaf)
    {
        try
        {
            leafsPool.put(leaf);
        }
        catch (InterruptedException e)
        {
            // This will never happen.
        }
    }
}
