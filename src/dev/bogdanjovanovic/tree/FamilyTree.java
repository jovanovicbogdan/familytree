package dev.bogdanjovanovic.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

//  public void addRelationship(final FamilyMember fromFamilyMember,
//      final FamilyMember toFamilyMember, final RelationshipType relationshipType) {
//    fromFamilyMember.addRelationship(toFamilyMember, relationshipType);
//
//    switch (relationshipType) {
//      case SON -> {
//        // check if there is a "HUSBAND" or "WIFE" relationship relatively to the person who
//        // is entering a new family member. If it does ask user to confirm relationship between
//        // found person and the family member being entered otherwise create new family member
//        // with the opposite relationship.
//        break;
//      }
//      case WIFE -> {
//        // add new FamilyMember with WIFE Relationship
//        break;
//      }
//      default -> {
//        System.out.println("Relationship not supported.");
//        System.exit(1);
//      }
//    }
//  }

  public Optional<FamilyMember> getFamilyMemberByPersonId(final int personId) {
    return this.familyMembers
        .stream()
        .filter(familyMember -> familyMember.getPerson().getPersonId() == personId)
        .findFirst();
  }

  public void printFamilyTree() {
    for (final FamilyMember familyMember : this.familyMembers) {
      System.out.println(familyMember.toString());
    }
  }

}
