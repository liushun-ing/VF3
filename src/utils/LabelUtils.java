package utils;

public class LabelUtils {

  /**
   * 获取str对应的EdgeLabel
   *
   * @param str 字符串
   * @return EdgeLabel
   */
  public static EdgeLabel getEdgeLabel(String str) {
    EdgeLabel edgeLabel = null;
    if (str.equals(EdgeLabel.CALL.getRelation())) {
      edgeLabel = EdgeLabel.CALL;
    } else if (str.equals(EdgeLabel.DECLARE.getRelation())) {
      edgeLabel = EdgeLabel.DECLARE;
    } else if (str.equals(EdgeLabel.INHERIT.getRelation())) {
      edgeLabel = EdgeLabel.INHERIT;
    } else if (str.equals(EdgeLabel.IMPLEMENT.getRelation())) {
      edgeLabel = EdgeLabel.IMPLEMENT;
    }
    return edgeLabel;
  }
}
