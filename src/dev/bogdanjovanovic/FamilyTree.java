package dev.bogdanjovanovic;

import java.util.ArrayList;
import java.util.List;

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
  }

  public void printFamilyTree() {
    for (final FamilyMember familyMember : this.familyMembers) {
      System.out.println(familyMember.toString());
    }
  }

  public static void main(final String[] args) {
    final FamilyTree bogdansFamilyTree = new FamilyTree();

    final FamilyMember bogdan = bogdansFamilyTree.addFamilyMember(new Person("Bogdan", "Jovanovic", 25));
    final FamilyMember mirjana = bogdansFamilyTree.addFamilyMember(new Person("Mirjana", "Nikolic", 53));

    bogdansFamilyTree.addRelationship(bogdan, mirjana, RelationshipType.SON);
    bogdansFamilyTree.addRelationship(mirjana, bogdan, RelationshipType.MOTHER);

    bogdansFamilyTree.printFamilyTree();
  }

}
