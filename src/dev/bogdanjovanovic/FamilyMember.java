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

  public void addRelationship(final FamilyMember secondFamilyPerson,
      final RelationshipType relationshipType) {
    this.relationships.add(new Relationship(this, secondFamilyPerson, relationshipType));
  }

  public Person getPerson() {
    return person;
  }

  @Override
  public String toString() {
    final StringBuilder message = new StringBuilder();

    for (final Relationship relationship : this.relationships) {
      message
          .append(relationship.getFirstFamilyMember().getPerson().forename())
          .append(" ")
          .append(relationship.getFirstFamilyMember().getPerson().surname())
          .append(" ==> ");

      message
          .append("(")
          .append(relationship.getRelationshipType())
          .append(")");

      message
          .append(" ==> ")
          .append(relationship.getSecondFamilyMember().getPerson().forename())
          .append(" ")
          .append(relationship.getSecondFamilyMember().getPerson().surname());
    }

    return message.toString();
  }

}
