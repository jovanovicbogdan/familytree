package dev.bogdanjovanovic.tree;

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

  public List<Relationship> getRelationships() {
    return relationships;
  }

  @Override
  public String toString() {
    final StringBuilder message = new StringBuilder();

    for (final Relationship relationship : this.relationships) {
      message
          .append(relationship.getFromFamilyMember().getPerson().getForename())
          .append(" ")
          .append(relationship.getFromFamilyMember().getPerson().getSurname())
          .append(" ==> ");

      message
          .append("(")
          .append(relationship.getRelationshipType())
          .append(")");

      message
          .append(" ==> ")
          .append(relationship.getToFamilyMember().getPerson().getForename())
          .append(" ")
          .append(relationship.getToFamilyMember().getPerson().getSurname());
    }

    return message.toString();
  }

}
