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

  /**
   * 判断两个节点的原型是否相同，只要有相同的就行
   *
   * @param label1 标签1
   * @param label2 标签2
   * @return 是否相同
   */
  public static boolean vertexLabelEqual(String label1, String label2) {
    String[] s1 = label1.split("-");
    String[] s2 = label2.split("-");
    for (String s : s1) {
      for (String value : s2) {
        if (s.equals(value)) {
          return true;
        }
      }
    }
    return false;
  }
}
