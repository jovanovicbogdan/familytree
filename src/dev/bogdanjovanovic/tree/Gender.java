package dev.bogdanjovanovic.tree;

public enum Gender {
  MALE("male"),
  FEMALE("female");

  private final String name;

  Gender(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
