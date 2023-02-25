package utils;

public enum EdgeLabel {
  DECLARE("declare"),
  CALL("call"),
  INHERIT("inherit"),
  IMPLEMENT("implement");

  private final String relation;

  EdgeLabel(String relation) {
    this.relation = relation;
  }

  public String getRelation() {
    return relation;
  }
}
