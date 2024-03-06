package dev.bogdanjovanovic.options.handlers;

import dev.bogdanjovanovic.PromptUser;
import dev.bogdanjovanovic.options.services.UpdateFamilyMemberService;
import dev.bogdanjovanovic.tree.FamilyTree;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateOrUpdateFamilyMemberOptionHandler {

  private final Scanner scanner;
  private final FamilyTree familyTree;
  private final PromptUser promptUser;

  public CreateOrUpdateFamilyMemberOptionHandler(final Scanner scanner,
      final FamilyTree familyTree, final PromptUser promptUser) {
    this.scanner = scanner;
    this.familyTree = familyTree;
    this.promptUser = promptUser;
  }

  public void handle() {
    final Map<Integer, String> options = new HashMap<>();
    options.put(1, "Update person in a family tree");
    options.put(2, "Create new person");

    promptUser.setOptions(options);

    final int chosenOption = promptUser.prompt();

    if (chosenOption == 1) {
      // update person based on personId
      final UpdateFamilyMemberService updateFamilyMemberService = new UpdateFamilyMemberService(
          scanner, familyTree
      );
      updateFamilyMemberService.updatePerson();
    }

    if (chosenOption == 2) {
      // create new person relatively to selected/entered personId
      createPerson();
    }
  }

  private void createPerson() {
    System.out.print("Enter the ID of the person relative to which you are adding a new person: ");
  }
}
