# Family Tree Manager

## Overview

Family Tree Manager is a console application developed in Java that allows users to create and manage family trees. Utilizing a graph data structure, the program provides functionalities to add, edit, and display family members and their relationships.

## Features

- **Load and Save Family Tree:** Users can load a family tree from a file or save the current family tree to a file for persistent storage. The family tree is serialized into bytes when saved to a file for enhanced performance.
- **Add Family Members:** Users can add new family members to the family tree, specifying details such as name, gender, birthdate, and relationship to existing members.
- **Edit Family Member Details:** Users can edit the details of existing family members, including their personal information and relationships.
- **Display Family Tree:** The program provides options to display the family tree in various formats, including ancestral and descendant views.
- **Automatic Relationship Handling:** When adding a new family member, the program automatically manages relationships to ensure the family tree remains logically consistent. For example, adding a mother automatically prompts for the relationship with the father.

## Usage

1. **Start the Program:** Run the program to launch the console application.
2. **Navigate the Menu:** Use the menu options to perform various actions such as loading a family tree, adding a family member, or displaying the tree.
3. **Enter Details:** When prompted, enter the necessary details for the family member or relationship.
4. **Save Changes:** Remember to save your changes to the family tree before exiting the program.

## Implementation Details

- **Graph Data Structure:** The family tree is implemented as a graph, with each family member represented as a node and relationships represented as edges.
- **Java:** The program is written in Java, making use of object-oriented programming principles and data structures.

## Limitations

- **Simplistic Relationship Handling:** The program currently handles a limited set of relationship types and may not accommodate complex family structures.
- **Console-Based Interface:** The user interface is text-based and might not be as intuitive as a graphical interface.

## Future Enhancements

- **Graphical User Interface:** Implementing a GUI would make the program more user-friendly and accessible.
- **Expanded Relationship Types:** Adding support for more relationship types would allow for more accurate family tree representations.

## Getting Started

To run the Family Tree Manager, ensure you have Java installed on your system. Download the source code and compile it using a Java compiler. Run the compiled program to start managing your family tree.
