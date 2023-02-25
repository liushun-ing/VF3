package utils;

// 由于可能出现多个原型，暂时废弃
public enum VertexLabel {
  // 字段原型
  FIELD("FIELD"),

  // 类原型
  ENTITY("ENTITY"),
  MINIMAL_ENTITY("MINIMAL_ENTITY"),
  DATA_PROVIDER("DATA_PROVIDER"),
  COMMANDER("COMMANDER"),
  BOUNDARY("BOUNDARY"),
  FACTORY("FACTORY"),
  CONTROLLER("CONTROLLER"),
  PURE_CONTROLLER("PURE_CONTROLLER"),
  LARGE_CLASS("LARGE_CLASS"),
  LAZY_CLASS("LAZY_CLASS"),
  DEGENERATE("DEGENERATE"),
  DATA_CLASS("DATA_CLASS"),
  POOL("POOL"),
  INTERFACE("INTERFACE"),

  // 方法原型
  GET("GET"),
  PREDICATE("PREDICATE"),
  PROPERTY("PROPERTY"),
  VOID_ACCESSOR("VOID_ACCESSOR"),
  SET("SET"),
  COMMAND("COMMAND"),
  NON_VOID_COMMAND("NON_VOID_COMMAND"),
  CONSTRUCTOR("CONSTRUCTOR"),
  COPY_CONSTRUCTOR("COPY_CONSTRUCTOR"),
  DESTRUCTOR("DESTRUCTOR"),
  //  FACTORY("FACTORY"), 重复
  COLLABORATOR("COLLABORATOR"),
  //  CONTROLLER("CONTROLLER"), 重复
  LOCAL_CONTROLLER("LOCAL_CONTROLLER"),
  INCIDENTAL("INCIDENTAL"),
  EMPTY("EMPTY"),
  ABSTRACT("ABSTRACT");

  private final String stereotype;

  VertexLabel(String stereotype) {
    this.stereotype = stereotype;
  }

  public String getStereotype() {
    return stereotype;
  }
}
