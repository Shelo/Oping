package com.shelodev.oping2;

public class OpingTokenizer
{
    private IndexBuffer tokens;
    private char[] data;
    private int index;
    private int position;
    private int tokenLength;
    private int line;

    public OpingTokenizer()
    {
        ;
    }

    public void tokenize(char[] data)
    {
        this.data = data;
        this.index = 0;
        this.position = 0;
        this.tokenLength = 0;

        tokens = new IndexBuffer();

        process();
    }

    private void process()
    {
        while (position < data.length)
        {
            parseToken(data[position]);
        }

        // close the buffer.
        tokens.close();
    }

    private void parseToken(char c)
    {
        tokens.setPosition(index, position);

        // handy for simple tokens.
        tokenLength = 1;

        switch (c)
        {
            case '+':
                tokens.setType(index, Types.TOKEN_BRANCH_SYMBOL);
                break;

            case '-':
                tokens.setType(index, Types.TOKEN_LEAF_SYMBOL);
                break;

            case ' ':
                tokens.setType(index, Types.TOKEN_SPACE);
                break;

            case '\t':
                tokens.setType(index, Types.TOKEN_TAB);
                break;

            case '\n':
                tokens.setType(index, Types.TOKEN_LINE_BREAK);
                break;

            case ':':
                tokens.setType(index, Types.TOKEN_COLON);
                break;

            case ',':
                tokens.setType(index, Types.TOKEN_COMMA);
                break;

            case '\'':
                tokens.setType(index, Types.TOKEN_STRING);
                parseString(true);
                break;

            case '"':
                tokens.setType(index, Types.TOKEN_STRING);
                parseString(false);
                break;

            default:
                tokens.setType(index, Types.TOKEN_EXPRESSION);
                parseExpression();
        }

        position += tokenLength;
        tokens.setLength(index, tokenLength);

        index++;
    }

    /**
     * Parses a expression, which is simply a string that ends with special characters:
     * colon, comma, line break
     */
    private void parseExpression()
    {
        int tempPos = position;
        boolean end = false;

        while (!end)
        {
            if (tempPos >= data.length)
            {
                end = true;
                break;
            }

            switch (data[tempPos])
            {
                case ',':
                    end = true;
                    break;

                case ':':
                    end = true;
                    break;

                case '\n':
                    end = true;
                    break;

                default:
                    tempPos++;
            }
        }

        if (!end)
        {
            throw new OpingParserException("Expression did not ended well.");
        }

        tokenLength = tempPos - position;
    }

    private void parseString(boolean singleQuoted)
    {
        position += 1;
        tokens.setPosition(index, position);

        int tempPos = position;
        boolean end = false;

        while (!end)
        {
            if (tempPos >= data.length)
                break;

            switch (data[tempPos])
            {
                case '"':
                    if (!singleQuoted)
                    {
                        end = true;
                        break;
                    }
                    else
                    {
                        tempPos++;
                    }

                case '\'':
                    if (singleQuoted)
                    {
                        end = true;
                        break;
                    }
                    else
                    {
                        tempPos++;
                    }

                default:
                    tempPos++;
            }
        }

        if (!end)
        {
            throw new OpingParserException("String did not ended with a double quote.");
        }

        tokenLength = tempPos - position;

        // skip the double quote.
        position += 1;
    }

    public IndexBuffer getTokens()
    {
        return tokens;
    }
}
