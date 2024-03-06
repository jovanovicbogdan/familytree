package dev.bogdanjovanovic.tree;

public enum RelationshipType {
  MOTHER("mother"),
  FATHER("father"),
  SON("son"),
  DAUGHTER("daughter"),
  WIFE("wife"),
  HUSBAND("husband"),
  EXTRAMARITAL_PARTNER("extramarital partner");

  private final String name;

  RelationshipType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
