package com.shelodev;

import com.shelodev.oping.BranchStop;
import com.shelodev.oping.structure.Branch;
import com.shelodev.oping.OpingParser;

import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            testBranchStop();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void testForest() throws IOException
    {
        OpingParser parser = new OpingParser();

        ArrayList<Branch> root = parser.forestParsing("res/ToBeParsed.oping");

        for (Branch branch : root)
        {
            branch.printRecursive();
        }
    }

    public static void testBranchStop() throws IOException
    {
        BranchStop branchStop = new BranchStop()
        {
            @Override
            public void onBranch(Branch branch)
            {
                System.out.println(branch);
            }
        };

        OpingParser parser = new OpingParser();
        parser.eachBranchParse("res/ToBeParsed.oping", branchStop);
    }
}
