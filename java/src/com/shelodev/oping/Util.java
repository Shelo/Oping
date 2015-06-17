package com.shelodev.oping;

import java.io.IOException;

public class Util
{
    public static String removeNewLineChar(String line)
    {
        if (line.charAt(line.length() - 1) == '\n')
        {
            return line.substring(0, line.length() - 2);
        }

        // return the same line if it was fine.
        return line;
    }

    public static int getIndentationWidth(String line)
    {
        int width = 0;

        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);

            if (c == OpingParser.CHAR_SPACE || c == OpingParser.CHAR_TAB)
            {
                width++;
            }
            else
            {
                break;
            }
        }

        return width;
    }

    public static int getIndentationLevel(ParseState state, String line) throws IOException
    {
        int width = getIndentationWidth(line);

        // check if this is a valid width.
        if (width % state.getIndentationWidth() != 0)
        {
            throw new IOException(String.format("Error at line %d: Indentation error.", state.getLineNumber()));
        }

        return width / state.getIndentationWidth();
    }

    public static OpingParser.NodeType getNodeType(ParseState state, String nodeLine) throws IOException
    {
        char firstChar = nodeLine.charAt(0);

        if (firstChar == OpingParser.CHAR_BRANCH)
        {
            return OpingParser.NodeType.BRANCH;
        }
        else if (firstChar == OpingParser.CHAR_LEAF)
        {
            return OpingParser.NodeType.LEAF;
        }
        else
        {
            throw new IOException(String.format("Error at line %d: Not a valid type.", state.getLineNumber()));
        }
    }
}
