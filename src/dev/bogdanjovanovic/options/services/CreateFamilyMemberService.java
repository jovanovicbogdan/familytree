package dev.bogdanjovanovic.options.services;

import com.sun.security.jgss.GSSUtil;
import dev.bogdanjovanovic.IdGenerator;
import dev.bogdanjovanovic.PromptUser;
import dev.bogdanjovanovic.tree.FamilyMember;
import dev.bogdanjovanovic.tree.FamilyTree;
import dev.bogdanjovanovic.tree.Gender;
import dev.bogdanjovanovic.tree.Person;
import dev.bogdanjovanovic.tree.Relationship;
import dev.bogdanjovanovic.tree.RelationshipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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
          "Please enter person id relative to whom you want to add a new family member: ");
      int personId = scanner.nextInt();
      // read new line character
      scanner.nextLine();
      final FamilyMember me = familyTree.getFamilyMemberByPersonId(personId)
          .orElseThrow(() -> new IllegalArgumentException(
              "Person with id \"" + personId + "\" doesn't exist"));
      System.out.println(
          "You have chosen person with ID " + personId + ": " + me.getPerson()
              .getForename() + " " + me.getPerson().getSurname());
      System.out.println();
      final Map<Integer, String> options = getPossibleOptions(me);
      promptUser.setOptions(options);
      System.out.println("Possible relations for entry: ");
      int chosenOption = promptUser.prompt();

      final RelationshipType relationshipForEntry = RelationshipType.valueOf(
          options.get(chosenOption).toUpperCase().replace(" ", "_"));
      if (relationshipForEntry.equals(RelationshipType.FATHER) || relationshipForEntry.equals(
          RelationshipType.MOTHER)) {
        createParent(me, relationshipForEntry);
      }

      if (relationshipForEntry.equals(RelationshipType.SON) || relationshipForEntry.equals(
          RelationshipType.DAUGHTER)) {
        // add new person, no automatic creation of a child's second parent
        // createChild();
      }

      if (relationshipForEntry.equals(RelationshipType.SPOUSE) || relationshipForEntry.equals(
          RelationshipType.UNMARRIED_PARTNER)) {
        // createSpouseOrPartner();
      }

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

  private void createParent(final FamilyMember me,
      final RelationshipType relationshipTypeForEntry) {
    final List<RelationshipType> relationshipTypes = me.getRelationships()
        .stream()
        .map(Relationship::getRelationshipType)
        .toList();

    final RelationshipType secondParentRelationship =
        relationshipTypeForEntry.equals(RelationshipType.FATHER) ? RelationshipType.MOTHER
            : RelationshipType.FATHER;

    if (!relationshipTypes.contains(RelationshipType.MOTHER) && !relationshipTypes.contains(
        RelationshipType.FATHER)) {
      System.out.println(
          "There is no family member without relations relative to a person " + me.getPerson()
              .getPersonId()
              + " that can be assigned relation \""
              + secondParentRelationship.getName() + "\".");
      System.out.println(
          "After adding person whose relation relative to person " + me.getPerson()
              .getPersonId() + " is \""
              + relationshipTypeForEntry.getName()
              + "\", automatically new person will be created that will have relation relative to person "
              + me.getPerson().getPersonId() + " that will be \""
              + secondParentRelationship.getName() + "\".");
      final Gender newPersonGender =
          relationshipTypeForEntry.equals(RelationshipType.MOTHER) ? Gender.FEMALE : Gender.MALE;
      final Person newPerson = getNewPersonData(newPersonGender);

      System.out.print(
          "Do you want to add new person in a family tree loaded into memory [Y/n]: ");
      final String answer = scanner.nextLine().toLowerCase();
      System.out.println();
      if (answer.equals("n") || answer.equals("no")) {
        return;
      }

      final FamilyMember newFamilyMember = new FamilyMember(newPerson);
      familyTree.addFamilyMember(newFamilyMember);

      final Person secondParentPerson = new Person();
      secondParentPerson.setForename(secondParentRelationship.getName());
      secondParentPerson.setSurname("of " + me.getPerson().getFullName());
      final FamilyMember secondParent = new FamilyMember(secondParentPerson);

      familyTree.addFamilyMember(secondParent);

      System.out.println();

      System.out.println(
          "Choose a relation between \"" + newFamilyMember.getPerson().getPersonId() + " "
              + newFamilyMember.getPerson().getFullName() + "\" and \""
              + secondParentPerson.getPersonId() + " " + secondParentPerson.getFullName()
              + "\".");

      Map<Integer, String> options = new HashMap<>();
      options.put(1, RelationshipType.SPOUSE.getName());
      options.put(2, RelationshipType.UNMARRIED_PARTNER.getName());
      promptUser.setOptions(options);
      System.out.println("Possible relations for entry: ");
      int chosenOption = promptUser.prompt();

      me.addRelationship(newFamilyMember, relationshipTypeForEntry);
      me.addRelationship(secondParent, secondParentRelationship);

      final RelationshipType parentRelationshipTypeForEntry = RelationshipType.valueOf(
          options.get(chosenOption).toUpperCase().replace(" ", "_"));

      final RelationshipType childRelationship =
          me.getPerson().getGender().equals(Gender.MALE) ? RelationshipType.SON
              : RelationshipType.DAUGHTER;
      newFamilyMember.addRelationship(me, childRelationship);
      newFamilyMember.addRelationship(secondParent, parentRelationshipTypeForEntry);

      secondParent.addRelationship(me, childRelationship);
      secondParent.addRelationship(newFamilyMember, parentRelationshipTypeForEntry);
      System.out.println();
    }
  }

  private Person getNewPersonData(final Gender gender) {
    try {
      final Person person = new Person();
      System.out.print("Enter forename or press enter to remain empty [\"\"]: ");
      final String forename = scanner.nextLine();
      System.out.println("Forename [\"" + forename + "\"]");
      person.setForename(forename);

      System.out.print("Enter surname or press enter to remain empty [\"\"]: ");
      final String surname = scanner.nextLine();
      System.out.println("Surname [\"" + surname + "\"]");
      person.setSurname(surname);

      person.setGender(gender);

      System.out.println();

      final String formatted = """
          %-10s %d
          %-10s %s
          %-10s %s
          %-10s %s
          """.formatted(
          "Person ID:", person.getPersonId(),
          "Forename:", forename,
          "Surname:", surname,
          "Gender:", gender.getName()
      );
      System.out.println(formatted);

      return person;
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }
}
