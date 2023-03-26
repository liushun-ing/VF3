package test.generator;

import java.util.Random;

public class Constant {
  public static String[] CLASS_COMMON_ROLE = {
      "BOUNDARY",  // 0.0-0.5
      "COMMANDER", // 0.5-0.7
      "ENTITY", // 0.7-0.8
      "FACTORY", // 0.8-0.9
  };

  public static String[] CLASS_NO_ROLE = {
      "MINIMAL_ENTITY",
      "DATA_PROVIDER",
      "CONTROLLER",
      "PURE_CONTROLLER",
      "LARGE_CLASS",
      "LAZY_CLASS",
      "DEGENERATE",
      "DATA_CLASS",
      "POOL",
      "INTERFACE"
  };

  public static String[] METHOD_COMMON_ROLE = {
      "COLLABORATOR", // 0.0-0.5
      "COMMAND", // 0.5-0.65
      "SET", // 0.65-0.75
      "FACTORY", // 0.75-0.80
      "CONSTRUCTOR", // 0.80-0.85
      "INCIDENTAL" // 0.85-0.9
  };

  public static String[] METHOD_NO_ROEL = {
      "GET",
      "PREDICATE",
      "PROPERTY",
      "VOID_ACCESSOR",
      "NON_VOID_COMMAND",
      "COPY_CONSTRUCTOR",
      "DESTRUCTOR",
      "CONTROLLER",
      "LOCAL_CONTROLLER",
      "EMPTY",
      "ABSTRACT"
  };

  public static String getClassRole() {
    double key = Math.random();
    if (key <= 0.3) {
      return CLASS_COMMON_ROLE[0];
    } else if (key <= 0.4) {
      return CLASS_COMMON_ROLE[1];
    } else if (key <= 0.5) {
      return CLASS_COMMON_ROLE[2];
    } else if (key <= 0.6) {
      return CLASS_COMMON_ROLE[3];
    } else {
      Random random = new Random();
      return CLASS_NO_ROLE[random.nextInt(10)];
    }
  }

  public static String getMethodRole() {
    double key = Math.random();
    if (key <= 0.25) {
      return METHOD_COMMON_ROLE[0];
    } else if (key <= 0.35) {
      return METHOD_COMMON_ROLE[1];
    } else if (key <= 0.45) {
      return METHOD_COMMON_ROLE[2];
    } else if (key <= 0.5) {
      return METHOD_COMMON_ROLE[3];
    } else if (key <= 0.55) {
      return METHOD_COMMON_ROLE[4];
    }  else if (key <= 0.6) {
      return METHOD_COMMON_ROLE[5];
    } else {
      Random random = new Random();
      return METHOD_NO_ROEL[random.nextInt(11)];
    }
  }

  public static String getFieldRole() {
    return "FIELD";
  }

  public static String getLabel() {
    double random = Math.random();
    if (random < 0.5) {
      return "declare";
    } else if (random < 0.8) {
      return "call";
    } else {
      if (Math.random() <= 0.5) {
        return "implement";
      } else {
        return "inherit";
      }
    }
  }

}
