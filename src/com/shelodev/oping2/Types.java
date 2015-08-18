package com.shelodev.oping2;

public class Types
{
    public static final byte TOKEN_BRANCH_SYMBOL        = 0;
    public static final byte TOKEN_LEAF_SYMBOL          = 1;
    public static final byte TOKEN_COLON                = 2;
    public static final byte TOKEN_COMMA                = 3;
    public static final byte TOKEN_EXPRESSION           = 4;
    public static final byte TOKEN_STRING               = 5;
    public static final byte TOKEN_LINE_BREAK           = 6;
    public static final byte TOKEN_SPACE                = 7;
    public static final byte TOKEN_TAB                  = 8;

    public static final byte ELEMENT_BRANCH_START       = 0;
    public static final byte ELEMENT_LEAF_START         = 1;
    public static final byte ELEMENT_BRANCH_NAMESPACE   = 2;
    public static final byte ELEMENT_BRANCH_NAME        = 3;
    public static final byte ELEMENT_LEAF_VALUE         = 4;
    public static final byte ELEMENT_LEAF_KEY           = 5;
    public static final byte ELEMENT_INDENTATION_BLOCK  = 6;
}
