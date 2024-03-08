package dev.bogdanjovanovic;

import java.util.Map;
import java.util.Scanner;

public class PromptUser {

  private final Scanner scanner;
  private Map<Integer, String> options;

  public PromptUser(final Scanner scanner) {
    this.scanner = scanner;
  }

  public void setOptions(final Map<Integer, String> options) {
    this.options = options;
  }

  public int prompt() {
    while (true) {
      try {
        printOptions();
        final int chosenOption = scanner.nextInt();
        // read new line character
        scanner.nextLine();

        if (!options.containsKey(chosenOption)) {
          System.out.println("Please select a number between 1 and " + options.size());
          continue;
        }

        System.out.println(
            "You have chosen \"" + chosenOption + " " + options.get(chosenOption) + "\"");
        System.out.println();

        return chosenOption;
      } catch (Exception ex) {
        throw new IllegalArgumentException(ex.getMessage());
      }
    }
  }

  private void printOptions() {
    options.forEach((key, value) -> System.out.println(key + " " + value));
    System.out.println();
    System.out.print("Choose an option (enter number between 1 and " + this.options.size() + "): ");
  }

}
