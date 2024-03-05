package dev.bogdanjovanovic;

public class Relationship {

  private final FamilyMember firstFamilyMember;
  private final FamilyMember secondFamilyMember;
  private final RelationshipType relationshipType;

  public Relationship(final FamilyMember firstFamilyMember, final FamilyMember secondFamilyMember,
      final RelationshipType relationshipType) {
    this.firstFamilyMember = firstFamilyMember;
    this.secondFamilyMember = secondFamilyMember;
    this.relationshipType = relationshipType;
  }

  public FamilyMember getFirstFamilyMember() {
    return firstFamilyMember;
  }

  public FamilyMember getSecondFamilyMember() {
    return secondFamilyMember;
  }

  public RelationshipType getRelationshipType() {
    return relationshipType;
  }

}
