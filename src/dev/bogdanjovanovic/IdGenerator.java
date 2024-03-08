package dev.bogdanjovanovic;

public class IdGenerator {

  private static int ID = 0;

  public static int nextId() {
    ID++;
    return ID;
  }

}
