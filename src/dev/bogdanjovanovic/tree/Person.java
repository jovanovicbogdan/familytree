package dev.bogdanjovanovic.tree;

public class Person {
  private final int personId;
  private String forename;
  private String surname;
  private Gender gender;

  public Person(final int personId, final String forename, final String surname, final Gender gender) {
    this.personId = personId;
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

  public void printPerson(final Person person) {
    final String formatted = """
        %-10s %d
        %-10s %s
        %-10s %s
        %-10s %s
        """.formatted(
            "Person ID:", person.getPersonId(),
        "Forename:", person.getForename(),
        "Surname:", person.getSurname(),
        "Gender:", person.getGender()
    );
    System.out.println(formatted);
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
