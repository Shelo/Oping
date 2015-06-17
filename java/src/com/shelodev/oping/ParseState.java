package com.shelodev.oping;

import java.util.ArrayList;

/**
 * Intended to be a bundle of states used by the {@link OpingParser}.
 */
public class ParseState
{
    private OpingParser.Indentation indentationType = OpingParser.Indentation.NONE;
    private OpingParser.NodeType previousNodeType = OpingParser.NodeType.ROOT;
    private int previousIndentationLevel = 0;
    private Branch branches;
    private int indentationWidth = -1;
    private int lineNumber = 0;

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
}
