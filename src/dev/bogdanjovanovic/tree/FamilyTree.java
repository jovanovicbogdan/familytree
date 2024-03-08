package dev.bogdanjovanovic.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FamilyTree {

  private final List<FamilyMember> familyMembers;

  public FamilyTree() {
    this.familyMembers = new ArrayList<>();
  }

  public void addFamilyMember(final FamilyMember familyMember) {
    this.familyMembers.add(familyMember);
    System.out.println(
        "New family member \"" + familyMember.getPerson().getFullName() + "\" added with an ID of "
            + familyMember.getPerson().getPersonId() + ".");
  }

  public Optional<FamilyMember> getFamilyMemberByPersonId(final int personId) {
    return this.familyMembers
        .stream()
        .filter(familyMember -> familyMember.getPerson().getPersonId() == personId)
        .findFirst();
  }

  public void printFamilyTree() {
    for (final FamilyMember familyMember : this.familyMembers) {
      System.out.println(familyMember.toString());
    }
  }

}
