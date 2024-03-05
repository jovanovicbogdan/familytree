package dev.bogdanjovanovic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FamilyTree {

  private final List<FamilyMember> familyMembers;

  public FamilyTree() {
    this.familyMembers = new ArrayList<>();
  }

  public FamilyMember addFamilyMember(final Person person) {
    final FamilyMember familyMember = new FamilyMember(person);
    this.familyMembers.add(familyMember);

    return familyMember;
  }

  public void addRelationship(final FamilyMember firstFamilyMember,
      final FamilyMember secondFamilyMember, final RelationshipType relationshipType) {
    firstFamilyMember.addRelationship(secondFamilyMember, relationshipType);

    switch(relationshipType) {
      case SON -> {
        // check if there is a "HUSBAND" or "WIFE" relationship relatively to the person who
        // is entering a new family member. If it does ask user to confirm relationship between
        // found person and the family member being entered otherwise create new family member
        // with the opposite relationship.
        break;
      }
      case WIFE -> {
        // add new FamilyMember with WIFE Relationship
        break;
      }
      default -> {
        System.out.println("Relationship not supported.");
        System.exit(1);
      }
    }
  }

  public void printFamilyTree() {
    for (final FamilyMember familyMember : this.familyMembers) {
      System.out.println(familyMember.toString());
    }
  }

  public static void main(final String[] args) {
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
    System.out.print("Choose an option: ");
    final int chosenOption = scanner.nextInt();
    System.out.println("You have chosen: " + chosenOption);
    scanner.close();
  }

}
