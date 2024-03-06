package dev.bogdanjovanovic;

import dev.bogdanjovanovic.options.handlers.CreateOrUpdateFamilyMemberOptionHandler;
import dev.bogdanjovanovic.tree.FamilyMember;
import dev.bogdanjovanovic.tree.FamilyTree;
import dev.bogdanjovanovic.tree.Person;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    final FamilyTree familyTree = new FamilyTree();
//
    final FamilyMember me = familyTree.addFamilyMember(new Person(1, "me", "", null));
//    final FamilyMember mirjana = bogdansFamilyTree.addFamilyMember(new Person("Mirjana", "Nikolic", 53));
//
//    bogdansFamilyTree.addRelationship(bogdan, mirjana, RelationshipType.SON);
//    bogdansFamilyTree.addRelationship(mirjana, bogdan, RelationshipType.MOTHER);
//
//    bogdansFamilyTree.printFamilyTree();

    final Scanner scanner = new Scanner(System.in);

    final Map<Integer, String> startingOptions = new HashMap<>();
    startingOptions.put(1, "Load family tree from a file");
    startingOptions.put(2, "Save family tree from memory to a file");
    startingOptions.put(3, "Create or update existing family tree loaded into memory");
    startingOptions.put(4, "Show family tree loaded into memory");
    startingOptions.put(5, "Close the program");

    final PromptUser promptUser = new PromptUser(scanner);

    int chosenOption;
    while (true) {
      try {
        promptUser.setOptions(startingOptions);
        chosenOption = promptUser.prompt();

        if (chosenOption == 3) {
          final CreateOrUpdateFamilyMemberOptionHandler createOrUpdateFamilyMemberOptionHandler = new CreateOrUpdateFamilyMemberOptionHandler(
              scanner, familyTree, promptUser);
          createOrUpdateFamilyMemberOptionHandler.handle();
        }

        if (chosenOption == 5) {
          break;
        }
      } catch (Exception ex) {
        scanner.close();
        throw new IllegalArgumentException(ex.getMessage());
      }
    }

    scanner.close();
  }

}
