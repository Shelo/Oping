package com.shelodev;

import com.shelodev.oping.Branch;
import com.shelodev.oping.OpingParser;

import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        OpingParser parser = new OpingParser();

        try
        {
            ArrayList<Branch> root = parser.forestParsing("res/ToBeParsed.oping");

            for (Branch branch : root)
            {
                branch.printRecursive();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
