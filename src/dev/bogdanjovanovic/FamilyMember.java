package dev.bogdanjovanovic;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember {

  private final Person person;
  private final List<Relationship> relationships;

  public FamilyMember(final Person person) {
    this.person = person;
    this.relationships = new ArrayList<>();
  }

  public void addRelationship(final FamilyMember toFamilyMember,
      final RelationshipType relationshipType) {
    this.relationships.add(new Relationship(this, toFamilyMember, relationshipType));
  }

  public Person getPerson() {
    return person;
  }

  @Override
  public String toString() {
    final StringBuilder message = new StringBuilder();

    for (final Relationship relationship : this.relationships) {
      message
          .append(relationship.getFromFamilyMember().getPerson().forename())
          .append(" ")
          .append(relationship.getFromFamilyMember().getPerson().surname())
          .append(" ==> ");

      message
          .append("(")
          .append(relationship.getRelationshipType())
          .append(")");

      message
          .append(" ==> ")
          .append(relationship.getToFamilyMember().getPerson().forename())
          .append(" ")
          .append(relationship.getToFamilyMember().getPerson().surname());
    }

    return message.toString();
  }

}
