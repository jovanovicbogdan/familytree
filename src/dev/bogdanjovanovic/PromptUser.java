package dev.bogdanjovanovic;

import java.util.Map;
import java.util.Scanner;

public class PromptUser {

  private final Scanner scanner;
  private Map<Integer, String> options;

  public PromptUser(final Scanner scanner, final Map<Integer, String> options) {
    this.scanner = scanner;
    this.options = options;
  }

  public void setOptions(final Map<Integer, String> options) {
    this.options = options;
  }

  public int prompt() {
    printOptions();
    return scanner.nextInt();
  }

  private void printOptions() {
    options.forEach((key, value) -> System.out.println(key + ". " + value));
    System.out.println();
    System.out.print("Choose an option (enter number between 1 and " + this.options.size() + "): ");
  }

}
