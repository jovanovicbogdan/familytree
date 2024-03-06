package dev.bogdanjovanovic.options.services;

import dev.bogdanjovanovic.tree.FamilyMember;
import dev.bogdanjovanovic.tree.FamilyTree;
import dev.bogdanjovanovic.tree.Gender;
import dev.bogdanjovanovic.tree.Person;
import java.util.Optional;
import java.util.Scanner;

public class UpdateFamilyMemberService {

  private final Scanner scanner;
  private final FamilyTree familyTree;

  public UpdateFamilyMemberService(final Scanner scanner, final FamilyTree familyTree) {
    this.scanner = scanner;
    this.familyTree = familyTree;
  }

  public void updatePerson() {
    try {
      System.out.print("Please enter person id you want to update: ");
      int personId = scanner.nextInt();
      // read new line character
      scanner.nextLine();
      final FamilyMember familyMember = familyTree.getFamilyMemberByPersonId(personId)
          .orElseThrow(() -> new IllegalArgumentException(
              "Person with id \"" + personId + "\" doesn't exist"));
      System.out.println(
          "You have chosen person with ID " + personId + ": " + familyMember.getPerson()
              .getForename() + " " + familyMember.getPerson().getSurname());
      updatePersonData(familyMember.getPerson());
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  private void updatePersonData(final Person person) {
    try {
      System.out.print(
          "Enter new forename or press enter to remain the same [\"" + person.getForename()
              + "\"]: ");
      final String forename = scanner.nextLine();
      System.out.println("Forename [\"" + forename + "\"]");

      System.out.print(
          "Enter new surname or press enter to remain the same [\"" + person.getSurname()
              + "\"]: ");
      final String surname = scanner.nextLine();
      System.out.println("Surname [\"" + surname + "\"]");

      String currentGender = "";
      if (Optional.ofNullable(person.getGender()).isPresent()) {
        currentGender = person.getGender().name();
      }
      System.out.print("Enter new gender or press enter to remain the same [\"" + currentGender
          + "\"] (male/female): ");
      final String gender = scanner.nextLine();
      System.out.println("Gender [\"" + gender + "\"]");

      System.out.println();

      final String formatted = """
          %-10s %d
          %-10s %s
          %-10s %s
          %-10s %s
          """.formatted(
          "Person ID:", person.getPersonId(),
          "Forename:", forename,
          "Surname:", surname,
          "Gender:", gender
      );
      System.out.println(formatted);

      System.out.print("Save (Y/n): ");
      final String save = scanner.nextLine();
      if (save.isEmpty() || save.equals("y") || save.equals("Y")) {
        System.out.println("Saving...");

        if (!forename.isEmpty()) {
          person.setForename(forename);
        }

        if (!surname.isEmpty()) {
          person.setSurname(surname);
        }

        if (!gender.isEmpty()) {
          person.setGender(Gender.valueOf(gender.toUpperCase()));
        }
      } else {
        System.out.println("Changes discarded");
      }
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex.getMessage());
    }
  }
}
