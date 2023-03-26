package utils;

import algorithm.MatchCouple;
import graph.Graph;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;

public class ArrayUtils {
  /**
   * 判断Integer列表中是否含有某个值
   *
   * @param list ArrayList<Integer> 整数列表
   * @param value int 目标找寻值
   * @return boolean 是否存在
   */
  public static boolean contains(ArrayList<Integer> list, int value) {
    for (Integer i : list) {
      if (i == value) {
        return true;
      }
    }
    return false;
  }

  /**
   * 通过节点 u 获取与之匹配的 v
   *
   * @param list 匹配节点对集合
   * @param id u的id
   * @return 匹配到的节点，否则为null
   */
  public static Vertex getVFromScByU(ArrayList<MatchCouple> list, int id) {
    for (MatchCouple mc : list) {
      if (mc.getU().getId() == id) {
        return mc.getV();
      }
    }
    return null;
  }

  /**
   * 判断一匹配的状态sc中是否有模式图的某个节点
   *
   * @param list 一匹配的状态sc
   * @param id 目标图节点id
   * @return 是否存在
   */
  public static boolean containsPatternGraphVertex(ArrayList<MatchCouple> list, int id) {
    for (MatchCouple mc : list) {
      if (mc.getU().getId() == id) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断一匹配的状态sc中是否有目标图的某个节点
   *
   * @param list 一匹配的状态sc
   * @param id 目标图节点id
   * @return 是否存在
   */
  public static boolean containsTargetGraphVertex(ArrayList<MatchCouple> list, int id) {
    for (MatchCouple mc : list) {
      if (mc.getV().getId() == id) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断映射节点对里是否存在与外部节点相关的边
   *
   * @param list 映射节点集合
   * @param id 外部节点
   * @param graph 图
   * @param graphType 图类型，0模式图 1目标图
   * @param type 关系类型，0前继 1后继
   * @return 是否存在
   */
  public static boolean containsEdgeOnScList(
      ArrayList<MatchCouple> list, int id, Graph graph, int graphType, int type) {
    for (MatchCouple mc : list) {
      if (type == 0) {
        if (graphType == 0) {
          if (graph.containsEdge(id, mc.getU().getId())) {
            return true;
          }
        } else {
          if (graph.containsEdge(id, mc.getV().getId())) {
            return true;
          }
        }
      } else {
        if (graphType == 0) {
          if (graph.containsEdge(mc.getU().getId(), id)) {
            return true;
          }
        } else {
          if (graph.containsEdge(mc.getV().getId(), id)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public static double getPl(HashMap<String, Integer> target, String label) {
    double pl = 0.0;
    for (String l : target.keySet()) {
      if (LabelUtils.vertexLabelEqual(l, label)) {
        pl += target.get(l);
      }
    }
    return pl;
  }
}
