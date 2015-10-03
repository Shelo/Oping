package com.shelodev.oping2;

import com.shelodev.oping2.structure.Branch;
import com.shelodev.oping2.structure.Leaf;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TreeBuilder
{
    private static Branch root;
    private static IndexBuffer elements;
    private static char[] data;

    private static int index;

    public static Branch build(IndexBuffer elements, char[] data)
    {
        TreeBuilder.root = new Branch();
        TreeBuilder.elements = elements;
        TreeBuilder.data = data;

        index = 0;

        process();

        // TODO: adjust everything to return just one branch.
        if (root.getBranches().size() == 1)
            return root.getBranches().get(0);

        return null;
    }

    private static void process()
    {
        while (index < elements.getSize())
            buildBranch(root);
    }

    private static void buildBranch(Branch parent)
    {
        int indentation = countIndentation();

        byte type = elements.getType(index);

        if (type != Types.ELEMENT_BRANCH_START)
            throw new OpingParserException("Expected branch.");

        // skip branch start.
        index++;

        Branch branch = new Branch();
        parent.addBranch(branch);

        // set the branch data.
        if (elements.getType(index) == Types.ELEMENT_BRANCH_NAMESPACE)
        {
            String namespace = getDataSubString(index);
            String name = getDataSubString(index + 1);
            branch.setData(namespace, name);

            index += 2;
        }
        else
        {
            String name = getDataSubString(index);
            branch.setName(name);

            index++;
        }

        if (index >= elements.getSize())
            return;

        // get all leafs and branches.
        int innerIndentation = indentation + 1;
        while (innerIndentation > indentation)
        {
            if (index >= elements.getSize())
                return;

            type = elements.getType(index);
            switch (type)
            {
                case Types.ELEMENT_INDENTATION_BLOCK:
                    innerIndentation = countIndentation();
                    break;

                case Types.ELEMENT_LEAF_START:
                    buildLeaf(branch);
                    break;

                case Types.ELEMENT_BRANCH_START:
                    index -= innerIndentation;
                    buildBranch(branch);
                    break;

                default:
                    System.out.println("WHAT!!");
            }
        }

        index -= innerIndentation;
    }

    private static void buildLeaf(Branch branch)
    {
        // skip leaf start.
        index++;

        // get leaf name.
        if (elements.getType(index) != Types.ELEMENT_LEAF_KEY)
            throw new OpingParserException("Expected leaf key.");

        String name = getDataSubString(index);
        Leaf leaf = new Leaf(name);
        branch.addLeaf(leaf);
        index++;

        // get all values.
        while (index < elements.getSize() && elements.getType(index) == Types.ELEMENT_LEAF_VALUE)
        {
            leaf.addValue(getDataSubString(index));
            index++;
        }
    }

    private static int countIndentation()
    {
        int i;
        for (i = 0; elements.getType(index) == Types.ELEMENT_INDENTATION_BLOCK; i++, index++);
        return i;
    }

    private static String getDataSubString(int element)
    {
        int position = elements.getPosition(element);
        int length = elements.getLength(element);

        StringBuilder result = new StringBuilder();

        for (int i = position; i < position + length; i++)
            result.append(data[i]);

        return result.toString();
    }

}
