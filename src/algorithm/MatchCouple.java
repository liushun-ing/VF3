package algorithm;

import graph.Vertex;

/** MatchCouple 匹配节点对类 */
public class MatchCouple {
  private Vertex u; // 模式图
  private Vertex v; // 目标图

  public MatchCouple() {}

  public MatchCouple(Vertex u, Vertex v) {
    this.u = u;
    this.v = v;
  }

  public Vertex getU() {
    return u;
  }

  public void setU(Vertex u) {
    this.u = u;
  }

  public Vertex getV() {
    return v;
  }

  public void setV(Vertex v) {
    this.v = v;
  }

  @Override
  public String toString() {
    return "MatchCouple{" + "u=" + u + ", v=" + v + '}';
  }
}
