package dev.bogdanjovanovic.tree;

import java.io.Serializable;

public class Relationship implements Serializable {

  private final FamilyMember fromFamilyMember;
  private final FamilyMember toFamilyMember;
  private final RelationshipType relationshipType;

  public Relationship(final FamilyMember fromFamilyMember, final FamilyMember toFamilyMember,
      final RelationshipType relationshipType) {
    this.fromFamilyMember = fromFamilyMember;
    this.toFamilyMember = toFamilyMember;
    this.relationshipType = relationshipType;
  }

  public FamilyMember getFromFamilyMember() {
    return fromFamilyMember;
  }

  public FamilyMember getToFamilyMember() {
    return toFamilyMember;
  }

  public RelationshipType getRelationshipType() {
    return relationshipType;
  }

}
