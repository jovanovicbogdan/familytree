package dev.bogdanjovanovic.options;

import dev.bogdanjovanovic.Person;
import dev.bogdanjovanovic.PromptUser;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateOrUpdateFamilyMemberOptionProcessor {

  private final Scanner scanner;
  private final PromptUser promptUser;

  public CreateOrUpdateFamilyMemberOptionProcessor(final Scanner scanner, final PromptUser promptUser) {
    this.scanner = scanner;
    this.promptUser = promptUser;
  }

  public void process() {
    final Map<Integer, String> options = new HashMap<>();
    options.put(1, "Update person in a family tree");
    options.put(2, "Create new person");

    int chosenOption = printOptions(options);

    if (chosenOption == 1) {
      // update person based on personId
      updatePerson();
    }

    if (chosenOption == 2) {
      // create new person relatively to selected/entered personId
//      createPerson();
    }
  }

  private int printOptions(final Map<Integer, String> options) {
    promptUser.setOptions(options);
    int chosenOption;
    while (true) {
      chosenOption = promptUser.prompt();

      if (!options.containsKey(chosenOption)) {
        System.out.println("Please select a number between 1 and " + options.size());
        continue;
      }

      return chosenOption;
    }
  }

  private void updatePerson() {
    System.out.println("Please enter person id you want to update: ");
  }

  private void createPerson(final Person person) {
    System.out.println("Possible relations for person " + person.forename());
  }

}
