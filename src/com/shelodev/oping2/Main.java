package com.shelodev.oping2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        parser.parse(data);
        parser.getElements().debug(data);
    }
}
