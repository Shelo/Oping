package com.shelodev.oping2;

public class OpingParser
{
    private OpingTokenizer tokenizer;
    private IndexBuffer elements;
    private IndexBuffer tokens;
    private int tokenIndex;
    private int elementIndex;
    private int indentationBlockLength;

    private boolean firstIndentation = true;
    private boolean catchIndentation = false;

    public OpingParser()
    {
        tokenizer = new OpingTokenizer();
    }

    public void parse(char[] data)
    {
        this.tokenIndex = 0;
        this.elementIndex = 0;

        elements = new IndexBuffer();

        tokenizer.tokenize(data);
        tokens = tokenizer.getTokens();

        process();
    }

    private void process()
    {
        while (tokenIndex < tokens.getSize())
        {
            parseElements();
        }

        elements.close();
    }

    private void parseElements()
    {
        byte type = tokens.getType(tokenIndex);

        switch (type)
        {
            case Types.TOKEN_LINE_BREAK:
                // skip any line breaks.
                tokenIndex++;
                break;

            case Types.TOKEN_BRANCH_SYMBOL:
                parseBranch();
                break;

            case Types.TOKEN_LEAF_SYMBOL:
                parseLeaf();
                break;

            case Types.TOKEN_SPACE:
            case Types.TOKEN_TAB:
                catchIndentation = true;
                parseIndentation(type);
                break;
        }
    }

    private void parseBranch()
    {
        pushElement(Types.ELEMENT_BRANCH_START);

        skipSpacesAndTabs();

        byte colon = tokens.getType(tokenIndex + 1);

        if (colon == Types.TOKEN_COLON)
        {
            // push namespace and name.
            pushElement(Types.ELEMENT_BRANCH_NAMESPACE);
            tokenIndex++;
            pushElement(Types.ELEMENT_BRANCH_NAME);
        }
        else
        {
            pushElement(Types.ELEMENT_BRANCH_NAME);
        }
    }

    private void parseLeaf()
    {
        pushElement(Types.ELEMENT_LEAF_START);
        skipSpacesAndTabs();

        byte key = tokens.getType(tokenIndex);
        byte colon = tokens.getType(tokenIndex + 1);

        if (key == Types.TOKEN_EXPRESSION && colon == Types.TOKEN_COLON)
        {
            // save the leaf name.
            pushElement(Types.ELEMENT_LEAF_KEY);
            tokenIndex++;

            // skip white space between colon and values.
            skipSpacesAndTabs();

            byte type = tokens.getType(tokenIndex);
            while (type != Types.TOKEN_LINE_BREAK)
            {
                switch (type)
                {
                    case Types.TOKEN_EXPRESSION:
                        pushElement(Types.ELEMENT_LEAF_VALUE);
                        break;

                    case Types.TOKEN_STRING:
                        pushElement(Types.ELEMENT_LEAF_VALUE);
                        break;

                    case Types.TOKEN_COMMA:
                        tokenIndex++;
                        break;

                    default:
                        throw new OpingParserException("Error in leaf values.");
                }

                skipSpacesAndTabs();

                if (tokenIndex >= tokens.getSize())
                    break;

                type = tokens.getType(tokenIndex);
            }
        }
        else
        {
            throw new OpingParserException("Bad leaf.");
        }
    }

    private int parseIndentation(byte type)
    {
        if (catchIndentation && firstIndentation)
        {
            firstIndentation = false;
            catchIndentation = false;
            indentationBlockLength = calcIndentationBlockLength(type);
            pushElement(Types.ELEMENT_INDENTATION_BLOCK);
            tokenIndex += indentationBlockLength - 1;
            return 1;
        }
        else
        {
            int length = calcIndentationBlockLength(type);

            int level = length / indentationBlockLength;

            for (int i = 0; i < level; i++)
            {
                pushElement(Types.ELEMENT_INDENTATION_BLOCK);
                tokenIndex += indentationBlockLength - 1;
            }

            return level;

        }
    }

    private int calcIndentationBlockLength(byte type)
    {
        int tempIndex = tokenIndex;

        if (type == Types.TOKEN_SPACE)
        {
            while (type == Types.TOKEN_SPACE)
            {
                if (tempIndex >= tokens.getSize())
                    break;

                type = tokens.getType(++tempIndex);
            }
        }
        else
        {
            while (type == Types.TOKEN_TAB)
            {
                if (tempIndex >= tokens.getSize())
                    break;

                type = tokens.getType(++tempIndex);
            }
        }

        return tempIndex - tokenIndex;
    }

    private void skipSpacesAndTabs()
    {
        if (tokenIndex >= tokens.getSize())
        {
            return;
        }

        byte type = tokens.getType(tokenIndex);
        while (type == Types.TOKEN_SPACE || type == Types.TOKEN_TAB)
        {
            if (tokenIndex >= tokens.getSize())
                break;

            type = tokens.getType(++tokenIndex);
        }
    }

    private void pushElement(byte type)
    {
        elements.setPosition(elementIndex, tokens.getPosition(tokenIndex));
        elements.setLength(elementIndex, tokens.getLength(tokenIndex));
        elements.setType(elementIndex, type);

        elementIndex++;
        tokenIndex++;
    }

    public IndexBuffer getElements()
    {
        return elements;
    }
}
