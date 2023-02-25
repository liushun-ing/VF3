package graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
  private ArrayList<Vertex> vertices;
  private ArrayList<Edge> edges;
  private HashMap<String, Integer> vertexLabels;

  /**
   * 添加节点
   *
   * @param vertex 节点
   */
  public void addVertex(Vertex vertex) {
    this.vertices.add(vertex);
  }

  /**
   * 添加边
   *
   * @param edge 边
   */
  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  /** 统计图的各个节点的入度和出度 */
  public void calculateInAndOutDegree() {
    for (Edge e : this.edges) {
      Vertex vo = this.getVertexById(e.getStartV().getId());
      vo.setOutDegree(vo.getOutDegree() + 1);
      Vertex vi = this.getVertexById(e.getEndV().getId());
      vi.setInDegree(vi.getInDegree() + 1);
    }
  }

  /**
   * 通过id获取节点
   *
   * @param id id
   * @return 匹配的节点，否则返回null
   */
  public Vertex getVertexById(int id) {
    for (Vertex v : this.vertices) {
      if (v.getId() == id) {
        return v;
      }
    }
    return null;
  }

  /**
   * 判断图中是否存在边
   *
   * @param inId 起始节点id
   * @param outId 终止节点id
   * @return 是否存在
   */
  public boolean containsEdge(int inId, int outId) {
    for (Edge e : this.getEdges()) {
      if (e.getStartV().getId() == inId && e.getEndV().getId() == outId) {
        return true;
      }
    }
    return false;
  }

  /**
   * 查找图中是否存在一条边，并返回该条边,不判断label
   *
   * @param inId in节点
   * @param outId out节点
   * @return 是否存在
   */
  public Edge getSpecificEdge(int inId, int outId) {
    for (Edge e : this.getEdges()) {
      if (e.getStartV().getId() == inId && e.getEndV().getId() == outId) {
        return e;
      }
    }
    return null;
  }

  public Graph() {}

  public Graph(ArrayList<Vertex> vertices, ArrayList<Edge> edges) {
    this.vertices = vertices;
    this.edges = edges;
    this.vertexLabels = new HashMap<>();
    // 统计图中出现的节点类型和数量
    for (Vertex v : vertices) {
      if (this.vertexLabels.containsKey(v.getLabel())) {
        this.vertexLabels.replace(v.getLabel(), this.vertexLabels.get(v.getLabel()) + 1);
      } else {
        this.vertexLabels.put(v.getLabel(), 1);
      }
    }
    calculateInAndOutDegree();
  }

  public Graph(
      ArrayList<Vertex> vertices, ArrayList<Edge> edges, HashMap<String, Integer> vertexLabels) {
    this.vertices = vertices;
    this.edges = edges;
    this.vertexLabels = vertexLabels;
  }

  public HashMap<String, Integer> getVertexLabels() {
    return vertexLabels;
  }

  public void setVertexLabels(HashMap<String, Integer> vertexLabels) {
    this.vertexLabels = vertexLabels;
  }

  public ArrayList<Vertex> getVertices() {
    return vertices;
  }

  public void setVertices(ArrayList<Vertex> vertices) {
    this.vertices = vertices;
  }

  public ArrayList<Edge> getEdges() {
    return edges;
  }

  public void setEdges(ArrayList<Edge> edges) {
    this.edges = edges;
  }

  @Override
  public String toString() {
    return "Graph{"
        + "vertices="
        + vertices
        + ", edges="
        + edges
        + ", vertexLabels="
        + vertexLabels
        + '}';
  }
}
