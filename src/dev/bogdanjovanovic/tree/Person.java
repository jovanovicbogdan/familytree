package dev.bogdanjovanovic.tree;

import dev.bogdanjovanovic.utils.IdGenerator;

public class Person {

  private int personId;
  private String forename;
  private String surname;
  private Gender gender;

  public Person() {
    this.personId = IdGenerator.nextId();
  }

  public Person(final String forename, final String surname,
      final Gender gender) {
    this.personId = IdGenerator.nextId();
    this.forename = forename;
    this.surname = surname;
    this.gender = gender;
  }

  public int getPersonId() {
    return personId;
  }

  public String getForename() {
    return forename;
  }

  public String getSurname() {
    return surname;
  }

  public Gender getGender() {
    return gender;
  }

  public void setForename(final String forename) {
    this.forename = forename;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public void setGender(final Gender gender) {
    this.gender = gender;
  }

  public String getFullName() {
//    if (forename == null) {
//      return surname;
//    }
//    if (surname == null) {
//      return forename;
//    }
//    if (forename == null && surname == null) {
//      return "";
//    }
    return forename + " " + surname;
  }

  @Override
  public String toString() {
    return """
        %-10s %d
        %-10s %s
        %-10s %s
        %-10s %s
        """.formatted(
        "Person ID:", personId, "Forename:", forename, "Surname:", surname, "Gender:", gender
    );
  }
}
