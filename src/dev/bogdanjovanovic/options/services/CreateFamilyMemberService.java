package dev.bogdanjovanovic.options.services;

import dev.bogdanjovanovic.PromptUser;
import dev.bogdanjovanovic.tree.FamilyMember;
import dev.bogdanjovanovic.tree.FamilyTree;
import dev.bogdanjovanovic.tree.Relationship;
import dev.bogdanjovanovic.tree.RelationshipType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CreateFamilyMemberService {

  private final Scanner scanner;
  private final FamilyTree familyTree;
  private final PromptUser promptUser;

  public CreateFamilyMemberService(final Scanner scanner, final FamilyTree familyTree,
      final PromptUser promptUser) {
    this.scanner = scanner;
    this.familyTree = familyTree;
    this.promptUser = promptUser;
  }

  public void createFamilyMember() {
    try {
      System.out.print(
          "Please enter person id relative to which you want to add a new family member: ");
      int personId = scanner.nextInt();
      // read new line character
      scanner.nextLine();
      final FamilyMember familyMember = familyTree.getFamilyMemberByPersonId(personId)
          .orElseThrow(() -> new IllegalArgumentException(
              "Person with id \"" + personId + "\" doesn't exist"));
      System.out.println(
          "You have chosen person with ID " + personId + ": " + familyMember.getPerson()
              .getForename() + " " + familyMember.getPerson().getSurname());
      System.out.println();
      final Map<Integer, String> options = getPossibleOptions(familyMember);
      promptUser.setOptions(options);
      System.out.println("Possible relations for input: ");
      final int chosenOption = promptUser.prompt();

      final String chosenOptionString = options.get(chosenOption);
      if (chosenOptionString.equals(RelationshipType.FATHER.getName()) || chosenOptionString.equals(
          RelationshipType.MOTHER.getName())) {
        // check if there is a person without relation relative to entered person ID that might be assigned as other parent
        // automatically create other parent
      }

      if (chosenOptionString.equals(RelationshipType.SON.getName()) || chosenOptionString.equals(
          RelationshipType.DAUGHTER.getName())) {
        // check if there is a spouse or unmarried partner without relation relative to entered person ID that might be assigned as other parent
        // if there is ask for relationship between child and the spouse relative to a person ID
        // if there is no spouse or unmarried partner relative to person ID create automatically a parent for child and ask about the relationship between created parent and person ID
      }

      // get data from user about new person
      // getData()
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  private Map<Integer, String> getPossibleOptions(final FamilyMember familyMember) {
    final Map<Integer, String> options = new HashMap<>();

    final List<Relationship> relationships = familyMember.getRelationships();

    final List<RelationshipType> relationshipTypes = relationships
        .stream()
        .map(Relationship::getRelationshipType)
        .toList();

    options.put(1, RelationshipType.SON.getName());
    options.put(2, RelationshipType.DAUGHTER.getName());

    int optionCounter = 3;
    if (!relationshipTypes.contains(RelationshipType.FATHER)) {
      options.put(optionCounter, RelationshipType.FATHER.getName());
      optionCounter++;
    }

    if (!relationshipTypes.contains(RelationshipType.MOTHER)) {
      options.put(optionCounter, RelationshipType.MOTHER.getName());
      optionCounter++;
    }

    if (!relationshipTypes.contains(RelationshipType.SPOUSE) || !relationshipTypes.contains(
        RelationshipType.UNMARRIED_PARTNER)) {
      options.put(optionCounter, RelationshipType.SPOUSE.getName());
      optionCounter++;
      options.put(optionCounter, RelationshipType.UNMARRIED_PARTNER.getName());
    }

    return options;
  }

}
