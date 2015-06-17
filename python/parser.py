from const import *
import re


class Oping(object):
    def __init__(self):
        self.root = None

        self.indentation_type = 0
        self.indentation_width = -1

        self.line_number = 0

        self.previous_indentation_level = 0
        self.previous_node_type = None

    def forest_parse(self, file_path):
        # create the tree root.
        self.root = list()

        with open(file_path) as read_file:
            # for each line in the file we will do a certain processing, after this, we should get
            # our self.root with some valid value.
            for line in read_file:
                # remove the last
                fixed_line = self.strip_new_line(line)

                self.line_number += 1

                # only consider non empty lines.
                stripped_line = fixed_line.strip()

                if stripped_line:
                    if stripped_line[0] == CHAR_COMMENT:
                        continue

                    self.process_line(fixed_line, stripped_line)

        return self.root

    def process_line(self, line, stripped_line):
        first_char = line[0]

        indentation_level = 0

        if first_char in INDENTATION_CHARS:
            # we'll do something different if this is an indented line.

            if self.indentation_width == -1:
                # at this point, this being the first indented line, we will calculate the spaces of
                # an indentation level, and from now on, this will be the rule.

                # save the indentation spaces.
                self.indentation_width = self.get_indentation_width(line)
                self.indentation_type = first_char

                # this should be the first level.
                indentation_level = 1

            else:
                indentation_level = self.get_indentation_level(line)

        self.process_node(indentation_level, stripped_line)

    def process_node(self, indentation_level, node_line):
        node_type = self.get_node_type(node_line)

        self.prove_node_validity(indentation_level, node_type, node_line)

        # get the corresponding branch for this node.
        branch = self.get_last_branch_at_level(indentation_level)

        if node_type == TYPE_BRANCH:
            node_name = self.get_branch_name(node_line)

            # if this is a branch, add a new branch to the largest branch.
            branch.append((node_name, []))

        else:
            node_name, node_values = self.get_leaf_attributes(node_line)

            # if this is a leaf, add the leaf.
            branch.append((node_name, node_values))

        self.previous_node_type = node_type
        self.previous_indentation_level = indentation_level

    def get_indentation_level(self, line):
        width = self.get_indentation_width(line)

        if width % self.indentation_width != 0:
            raise IndentationError("[l %d] There's an error in the indentation of the parsed file." % self.line_number)

        return width / self.indentation_width

    @staticmethod
    def get_indentation_width(line):
        spaces = 0

        for char in line:
            if char in INDENTATION_CHARS:
                spaces += 1
            else:
                break

        return spaces

    # TODO
    def prove_node_validity(self, indentation_level, node_type, node_line):
        if node_type == TYPE_LEAF:
            if self.previous_node_type is None:
                raise SyntaxError("[l %d] A leaf is not a tree." % self.line_number)

            elif self.previous_node_type == TYPE_LEAF and self.previous_indentation_level < indentation_level:
                raise SyntaxError("[l %d] This node is not indented well." % self.line_number)

            elif self.previous_node_type == TYPE_BRANCH and self.previous_indentation_level == indentation_level:
                raise SyntaxError("[l %d] The previous node is a branch with no leafs inside." % self.line_number)

    def get_node_type(self, node_line):
        first_char = node_line[0]

        if first_char == CHAR_LEAF:
            return TYPE_LEAF
        elif first_char == CHAR_BRANCH:
            return TYPE_BRANCH
        else:
            raise TypeError("[l %d] The node is not a leaf nor a branch." % self.line_number)

    @staticmethod
    def strip_new_line(line):
        """
        Remove last character if it is a line ending.
        :param line: the line to be stripped.
        :return: the new line.
        """

        if line[-1] == "\n":
            return line[0:-1]

        return line

    def get_last_branch_at_level(self, indentation_level):
        branch = self.root
        counter = 0

        while counter < indentation_level:
            counter += 1
            branch = branch[-1][-1]

        return branch

    def get_branch_name(self, node_line):
        types = re.search("\+ +([A-Za-z0-9_]+?)$", node_line)

        if types is None:
            raise SyntaxError("[l %d] There's a syntax error." % self.line_number)

        return types.group(1)

    def get_leaf_attributes(self, node_line):
        matches = re.search("\- +([a-zA-Z0-9]+?): *(.+[^,])$", node_line)

        if matches is None:
            raise SyntaxError("[l %d] There's a syntax error." % self.line_number)

        values = []

        latest_value = ""
        in_string = False

        for char in matches.group(2):
            if char == '"\'':
                in_string = not in_string

            if in_string:
                latest_value += char

            else:
                if char == ",":
                    values.append(latest_value)
                    latest_value = ""

                elif latest_value and char in INDENTATION_CHARS:
                    raise SyntaxError("[l %d] There's an error in the values." % self.line_number)

                elif latest_value or char not in INDENTATION_CHARS:
                    latest_value += char

        if latest_value:
            values.append(latest_value)

        return matches.group(1), values

if __name__ == '__main__':
    oping = Oping()
    parsed_tree = oping.forest_parse("ToBeParsed.oping")
    print parsed_tree
