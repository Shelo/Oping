package com.shelodev.oping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpingParser
{
    public static final char CHAR_SPACE = ' ';
    public static final char CHAR_TAB = '\t';
    public static final char CHAR_LEAF = '-';
    public static final char CHAR_BRANCH = '+';
    public static final char CHAR_COMMENT = '#';

    private Pattern leafPattern;
    private Pattern branchPattern;

    public enum NodeType
    {
        BRANCH,
        LEAF,
        ROOT,
    }

    public enum Indentation
    {
        SPACES,
        TABS,
        NONE,
    }

    /**
     * Construct the parser, this parser is intended to be used with any text,
     * so, use it as a tool.
     */
    public OpingParser()
    {
        // TODO: this is not totally valid.
        leafPattern = Pattern.compile("\\- +([A-Za-z0-9_]+?): *(.*)");
        branchPattern = Pattern.compile("\\+ +([A-Za-z0-9_]+?)$");
    }

    public ArrayList<Branch> forestParsing(final String filePath) throws IOException
    {
        Branch branches = new Branch(null);
        ParseState state = new ParseState();
        state.setBranchesRoot(branches);

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = reader.readLine()) != null)
        {
            // get a trimmed line, with only needed chars.
            String trimmedLine = line.trim();

            if (!trimmedLine.isEmpty())
            {
                // remove only newline char.
                String rawLine = Util.removeNewLineChar(line);

                state.incrementLineNumber();

                if (trimmedLine.charAt(0) == CHAR_COMMENT)
                {
                    continue;
                }

                processLine(rawLine, trimmedLine, state);
            }
        }

        return branches.getBranches();
    }

    public void processLine(String rawLine, String trimmedLine, ParseState state) throws IOException
    {
        char firstChar = rawLine.charAt(0);
        int indentationLevel = 0;

        if (firstChar == CHAR_SPACE || firstChar == CHAR_TAB)
        {
            if (state.getIndentationWidth() == -1)
            {
                // at this point, this being the first indented line, we will calculate the spaces of
                // an indentation level, and from now on, this will be the rule.

                state.setIndentationWidth(Util.getIndentationWidth(rawLine));
                state.setIndentationType(firstChar == CHAR_SPACE ? Indentation.SPACES : Indentation.TABS);

                // this should be the first level.
                indentationLevel = 1;
            }
            else
            {
                indentationLevel = Util.getIndentationLevel(state, rawLine);
            }
        }

        processNode(state, indentationLevel, trimmedLine);
    }

    public void processNode(ParseState state, int indentationLevel, String nodeLine) throws IOException
    {
        NodeType nodeType = Util.getNodeType(state, nodeLine);
        proveNodeIsValid(state, indentationLevel, nodeType, nodeLine);

        Branch branch = getLastBranchAtLevel(state, indentationLevel);

        if (nodeType == NodeType.BRANCH)
        {
            String branchName = findBranchName(state, nodeLine);
            Branch child = new Branch(branchName);
            branch.addBranch(child);
        }
        else
        {
            Leaf leaf = retrieveLeaf(state, nodeLine);
            branch.addLeaf(leaf);
        }

        state.setPreviousNodeType(nodeType);
        state.setPreviousIndentationLevel(indentationLevel);
    }

    private String findBranchName(ParseState state, String nodeLine) throws IOException
    {
        Matcher matcher = branchPattern.matcher(nodeLine);

        if (matcher.find())
        {
            return matcher.group(1);
        }

        throw new IOException(String.format("Error at line %d: Not a valid branch.", state.getLineNumber()));
    }

    private Leaf retrieveLeaf(ParseState state, String nodeLine) throws IOException
    {
        Matcher matcher = leafPattern.matcher(nodeLine);

        if (matcher.find())
        {
            Leaf leaf = new Leaf(matcher.group(1));

            String values = matcher.group(2);

            boolean inString = false;
            StringBuilder latestValue = new StringBuilder();
            for (int i = 0; i < values.length(); i++)
            {
                char c = values.charAt(i);

                if (c == '\'')
                {
                    inString = !inString;
                }

                if (inString)
                {
                    latestValue.append(c);
                }
                else
                {
                    if (c == ',')
                    {
                        leaf.addValue(latestValue.toString());
                        latestValue.delete(0, latestValue.length());
                    }
                    // remember: whe are not in a string!.
                    else if (latestValue.length() != 0 && (c == CHAR_SPACE || c == CHAR_TAB))
                    {
                        throw new IOException(String.format("Error at line %d: Syntax error in values.",
                                state.getLineNumber()));
                    }
                    else if (latestValue.length() != 0 || !(c == CHAR_SPACE || c == CHAR_TAB))
                    {
                        latestValue.append(c);
                    }
                }
            }

            if (latestValue.length() != 0)
            {
                leaf.addValue(latestValue.toString());
            }

            return leaf;
        }

        throw new IOException(String.format("Error at line %d: Not a valid branch.", state.getLineNumber()));
    }

    private Branch getLastBranchAtLevel(ParseState state, int indentationLevel)
    {
        Branch branch = state.getRoot();
        int counter = 0;

        while (counter++ < indentationLevel)
        {
            branch = branch.getLastBranch();
        }

        return branch;
    }

    private boolean proveNodeIsValid(ParseState state, int indentationLevel, NodeType nodeType, String nodeLine)
            throws IOException
    {
        // TODO: Do this.
        return true;
    }
}
