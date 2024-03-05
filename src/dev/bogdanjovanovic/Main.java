package dev.bogdanjovanovic;

import dev.bogdanjovanovic.options.CreateOrUpdateFamilyMemberOptionProcessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
//    final FamilyTree bogdansFamilyTree = new FamilyTree();
//
//    final FamilyMember bogdan = bogdansFamilyTree.addFamilyMember(new Person("Bogdan", "Jovanovic", 25));
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

    final PromptUser promptUser = new PromptUser(scanner, startingOptions);

    int chosenOption;
    while (true) {
      try {
        chosenOption = promptUser.prompt();

        if (!startingOptions.containsKey(chosenOption)) {
          System.out.println("Please select a number between 1 and " + startingOptions.size());
          continue;
        }

        System.out.println(
            "You have chosen \"" + chosenOption + ". " + startingOptions.get(chosenOption) + "\"");

        if (chosenOption == 3) {
          final CreateOrUpdateFamilyMemberOptionProcessor createOrUpdateFamilyMemberOptionProcessor = new CreateOrUpdateFamilyMemberOptionProcessor(
              scanner, promptUser);
          createOrUpdateFamilyMemberOptionProcessor.process();
        }

        if (chosenOption == 5) {
          break;
        }
      } catch (Exception ex) {
        System.out.println("Invalid input");
        scanner.close();
        System.exit(1);
      }
    }

    scanner.close();
  }

}
