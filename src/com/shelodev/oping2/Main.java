package com.shelodev.oping2;

import com.shelodev.oping2.structure.Branch;
import com.shelodev.oping2.structure.Leaf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        char[] data = "+ TreeRoot\n    - Leaf1".toCharArray();

        try
        {
            FileReader fileReader = new FileReader("res/ToBeParsed.oping");
            StringBuilder builder = new StringBuilder();

            int c;
            while ((c = fileReader.read()) != -1)
            {
                builder.append((char) c);
            }

            data = builder.toString().toCharArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        /*
        OpingTokenizer tokenizer = new OpingTokenizer();
        tokenizer.tokenize(data);
        tokenizer.getTokens().debug(data);
        */

        OpingParser parser = new OpingParser();
        ArrayList<Branch> branches = parser.parse(data);

        for (Branch branch : branches)
        {
            System.out.println(branch.toString(0));
        }
    }
}
