package dev.bogdanjovanovic.tree;

public enum RelationshipType {
  SON("Son"),
  DAUGHTER("Daughter"),
  FATHER("Father"),
  MOTHER("Mother"),
  SPOUSE("Spouse"),
//  EX("Ex"),
  UNMARRIED_PARTNER("Unmarried partner");

  private final String name;

  RelationshipType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
