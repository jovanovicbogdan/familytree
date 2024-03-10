package dev.bogdanjovanovic.options.services;

import dev.bogdanjovanovic.tree.FamilyMember;
import dev.bogdanjovanovic.tree.FamilyTree;
import dev.bogdanjovanovic.tree.Gender;
import dev.bogdanjovanovic.tree.Person;
import dev.bogdanjovanovic.tree.Relationship;
import dev.bogdanjovanovic.tree.RelationshipType;
import dev.bogdanjovanovic.utils.PromptUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        // createChild();
      }

      if (relationshipForEntry.equals(RelationshipType.SPOUSE) || relationshipForEntry.equals(
          RelationshipType.UNMARRIED_PARTNER) || relationshipForEntry.equals(
          RelationshipType.EX)) {
        createPartner(me, relationshipForEntry);
        System.out.println();
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
        RelationshipType.UNMARRIED_PARTNER) || !relationshipTypes.contains(
        RelationshipType.EX)) {
      options.put(optionCounter, RelationshipType.SPOUSE.getName());
      optionCounter++;
      options.put(optionCounter, RelationshipType.UNMARRIED_PARTNER.getName());
      optionCounter++;
      options.put(optionCounter, RelationshipType.EX.getName());
    }

    return options;
  }

  private void createParent(final FamilyMember me,
      final RelationshipType relationshipTypeForEntry) {
    final List<RelationshipType> relationshipTypes = me.getRelationships()
        .stream()
        .map(Relationship::getRelationshipType)
        .toList();

    if (!relationshipTypes.contains(RelationshipType.MOTHER) && !relationshipTypes.contains(
        RelationshipType.FATHER)) {
      final RelationshipType secondParentType =
          relationshipTypeForEntry.equals(RelationshipType.FATHER) ? RelationshipType.MOTHER
              : RelationshipType.FATHER;

      System.out.println(
          "There is no family member without relations relative to a person " + me.getPerson()
              .getPersonId()
              + " that can be assigned relation \""
              + secondParentType.getName() + "\".");
      System.out.println(
          "After adding person whose relation relative to person " + me.getPerson()
              .getPersonId() + " is \""
              + relationshipTypeForEntry.getName()
              + "\", automatically new person will be created that will have relation relative to person "
              + me.getPerson().getPersonId() + " that will be \""
              + secondParentType.getName() + "\".");

      createBothParents(me, secondParentType, relationshipTypeForEntry);

      System.out.println();
    }

    if (relationshipTypes.contains(RelationshipType.MOTHER) || relationshipTypes.contains(
        RelationshipType.FATHER)) {
      final FamilyMember parent = me.getRelationships()
          .stream()
          .filter(relationship -> relationship.getRelationshipType() == RelationshipType.MOTHER
              || relationship.getRelationshipType() == RelationshipType.FATHER)
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Parent not found."))
          .getToFamilyMember();

      System.out.println(
          "Relation found between you and \"" + parent.getPerson().getPersonId() + " "
              + parent.getPerson().getFullName() + "\" as a " + "\"" + parent + "\".");

      final Optional<Relationship> maybePotentialSecondParent = parent.getRelationships()
          .stream()
          .filter(relationship -> relationship.getRelationshipType().equals(RelationshipType.SPOUSE)
              || relationship.getRelationshipType().equals(RelationshipType.UNMARRIED_PARTNER)
              || relationship.getRelationshipType().equals(RelationshipType.EX))
          .findFirst();
      if (maybePotentialSecondParent.isPresent()) {
        final Relationship potentialSecondParent = maybePotentialSecondParent.get();
        final FamilyMember firstParentsSpouseOrPartner = potentialSecondParent.getToFamilyMember();

        System.out.println(
            "Please choose a relation between you and \"" + firstParentsSpouseOrPartner.getPerson()
                .getPersonId() + " " + firstParentsSpouseOrPartner.getPerson().getFullName()
                + "\".");
        System.out.println("Possible relations for entry: ");
        final Map<Integer, String> options = new HashMap<>();
        options.put(1, firstParentsSpouseOrPartner.getPerson().getGender().equals(Gender.MALE)
            ? RelationshipType.FATHER.getName() : RelationshipType.MOTHER.getName());
        options.put(2, "No relation");
        promptUser.setOptions(options);
        int chosenOption = promptUser.prompt();

        if (chosenOption == 1) {
          me.addRelationship(parent, relationshipTypeForEntry);

          final RelationshipType childRelationship = getChildRelationshipType(
              me.getPerson().getGender());
          parent.addRelationship(me, childRelationship);
        }

        if (chosenOption == 2) {
          createSingleParent(me, parent, relationshipTypeForEntry);
        }
      }
    }
  }

  private void createSingleParent(final FamilyMember me, final FamilyMember parent,
      final RelationshipType relationshipTypeForEntry) {
    final Optional<FamilyMember> maybeNewFamilyMember = addNewPersonToFamilyTree(
        relationshipTypeForEntry);

    if (maybeNewFamilyMember.isPresent()) {
      final FamilyMember newFamilyMember = maybeNewFamilyMember.get();

      me.addRelationship(newFamilyMember, relationshipTypeForEntry);

      final RelationshipType childRelationship = getChildRelationshipType(
          me.getPerson().getGender());
      newFamilyMember.addRelationship(me, childRelationship);

      System.out.println(
          "Please choose a relation between \"" + newFamilyMember.getPerson()
              .getPersonId() + " " + newFamilyMember.getPerson().getFullName() + "\" and \""
              + parent.getPerson().getPersonId() + " " + parent.getPerson().getFullName() + "\".");
      System.out.println("Possible relations for entry: ");
      final Map<Integer, String> options = new HashMap<>();
      options.put(1, RelationshipType.SPOUSE.getName());
      options.put(2, RelationshipType.UNMARRIED_PARTNER.getName());
      options.put(3, RelationshipType.EX.getName());
      promptUser.setOptions(options);
      int chosenOption = promptUser.prompt();

      final RelationshipType partnerRelationshipType = RelationshipType.valueOf(
          options.get(chosenOption).toUpperCase().replace(" ", "_"));

      newFamilyMember.addRelationship(parent, partnerRelationshipType);
      parent.addRelationship(newFamilyMember, partnerRelationshipType);
    }

  }

  private void createBothParents(final FamilyMember me,
      final RelationshipType secondParentType,
      final RelationshipType relationshipTypeForEntry) {
    final Optional<FamilyMember> maybeNewFamilyMember = addNewPersonToFamilyTree(
        relationshipTypeForEntry);

    if (maybeNewFamilyMember.isPresent()) {
      final FamilyMember newFamilyMember = maybeNewFamilyMember.get();

      final Person secondParentPerson = new Person();
      secondParentPerson.setForename(secondParentType.getName());
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
      options.put(3, RelationshipType.EX.getName());
      promptUser.setOptions(options);
      System.out.println("Possible relations for entry: ");
      int chosenOption = promptUser.prompt();

      me.addRelationship(newFamilyMember, relationshipTypeForEntry);
      me.addRelationship(secondParent, secondParentType);

      final RelationshipType parentRelationshipTypeForEntry = RelationshipType.valueOf(
          options.get(chosenOption).toUpperCase().replace(" ", "_"));

      final RelationshipType childRelationship = getChildRelationshipType(
          me.getPerson().getGender());
      newFamilyMember.addRelationship(me, childRelationship);
      newFamilyMember.addRelationship(secondParent, parentRelationshipTypeForEntry);

      secondParent.addRelationship(me, childRelationship);
      secondParent.addRelationship(newFamilyMember, parentRelationshipTypeForEntry);
    }
  }

  private void createPartner(final FamilyMember me,
      final RelationshipType relationshipTypeForEntry) {
    final Optional<FamilyMember> maybeNewFamilyMember = addNewPersonToFamilyTree(
        relationshipTypeForEntry);

    if (maybeNewFamilyMember.isPresent()) {
      final FamilyMember newFamilyMember = maybeNewFamilyMember.get();

      me.addRelationship(newFamilyMember, relationshipTypeForEntry);
      newFamilyMember.addRelationship(me, relationshipTypeForEntry);
    }
  }

  private Optional<FamilyMember> addNewPersonToFamilyTree(
      final RelationshipType relationshipTypeForEntry) {
    final Optional<Person> maybeNewPerson = getPersonData(relationshipTypeForEntry);

    if (maybeNewPerson.isEmpty()) {
      System.out.println("Person discarded");
      return Optional.empty();
    }

    final Person newPerson = maybeNewPerson.get();

    final FamilyMember newFamilyMember = new FamilyMember(newPerson);
    familyTree.addFamilyMember(newFamilyMember);

    return Optional.of(newFamilyMember);
  }

  private Optional<Person> getPersonData(final RelationshipType relationshipTypeForEntry) {
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

      final Gender gender;
      if (relationshipTypeForEntry.equals(RelationshipType.SPOUSE)
          || relationshipTypeForEntry.equals(RelationshipType.UNMARRIED_PARTNER)
          || relationshipTypeForEntry.equals(RelationshipType.EX)) {
        System.out.print("Enter gender (male/female): ");
        final String genderInput = scanner.nextLine();
        gender = Gender.valueOf(genderInput.toUpperCase());
        System.out.println("Gender [\"" + genderInput + "\"]");
      } else {
        gender = relationshipTypeForEntry.equals(RelationshipType.MOTHER)
            || relationshipTypeForEntry.equals(RelationshipType.DAUGHTER) ? Gender.FEMALE
            : Gender.MALE;
      }
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

      System.out.print(
          "Do you want to add new person in a family tree loaded into memory [Y/n]: ");
      final String answer = scanner.nextLine().toLowerCase();
      System.out.println();
      if (answer.equals("n") || answer.equals("no")) {
        System.out.println("Person discarded");
        return Optional.empty();
      }

      return Optional.of(person);
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  public RelationshipType getChildRelationshipType(final Gender gender) {
    return gender.equals(Gender.MALE) ? RelationshipType.SON : RelationshipType.DAUGHTER;
  }
}
