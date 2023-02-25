package algorithm;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import utils.EdgeLabel;
import utils.LabelUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** BuildGraph 图的构造类，读取文件，构造图结构 */
public class BuildGraph {

  /**
   * 读取文件，构造模式图集合
   *
   * @return 模式图集合
   */
  public ArrayList<Graph> buildPatternGraphs() {
    ArrayList<Graph> patternGraphs = new ArrayList<>();
    InputStream is = this.getClass().getResourceAsStream("/pattern_mylyn/pattern_mylyn.txt");
    if (is == null) {
      return patternGraphs;
    }
    BufferedReader pattern = new BufferedReader(new InputStreamReader(is));
    String lineStr;
    String[] line;
    try {
      while ((lineStr = pattern.readLine()) != null) {
        if (lineStr.startsWith("t")) {
          ArrayList<Vertex> vertices = new ArrayList<>();
          ArrayList<Edge> edges = new ArrayList<>();
          while ((lineStr = pattern.readLine()) != null
              && (lineStr.startsWith("v") || lineStr.startsWith("e"))) {
            if (lineStr.startsWith("v")) {
              line = lineStr.split(" ");
              Vertex v = new Vertex(Integer.parseInt(line[1]), line[2]);
              vertices.add(v);
            } else if (lineStr.startsWith("e")) {
              line = lineStr.split(" ");
              Vertex v1 = null, v2 = null;
              for (Vertex v : vertices) {
                if (v.getId() == Integer.parseInt(line[1])) {
                  v1 = v;
                }
                if (v.getId() == Integer.parseInt(line[2])) {
                  v2 = v;
                }
              }
              EdgeLabel edgeLabel = LabelUtils.getEdgeLabel(line[3]);
              if (edgeLabel != null) {
                // 必须是四种标签之一
                Edge e = new Edge(v1, v2, edgeLabel);
                edges.add(e);
              }
            }
          }
          Graph graph = new Graph(vertices, edges);
          patternGraphs.add(graph);
        }
      }
      pattern.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return patternGraphs;
  }

  /**
   * 构造目标图样例
   *
   * @return 目标图
   */
  public Graph buildTargetGraph() {
    Graph graph = new Graph();
    InputStream is = this.getClass().getResourceAsStream("/pattern_mylyn/target.txt");
    if (is == null) {
      return graph;
    }
    BufferedReader target = new BufferedReader(new InputStreamReader(is));
    String lineStr;
    String[] line;
    try {
      while ((lineStr = target.readLine()) != null) {
        if (lineStr.startsWith("t")) {
          ArrayList<Vertex> vertices = new ArrayList<>();
          ArrayList<Edge> edges = new ArrayList<>();
          while ((lineStr = target.readLine()) != null
              && (lineStr.startsWith("v") || lineStr.startsWith("e"))) {
            if (lineStr.startsWith("v")) {
              line = lineStr.split(" ");
              Vertex v = new Vertex(Integer.parseInt(line[1]), line[2]);
              vertices.add(v);
            } else if (lineStr.startsWith("e")) {
              line = lineStr.split(" ");
              Vertex v1 = null, v2 = null;
              for (Vertex v : vertices) {
                if (v.getId() == Integer.parseInt(line[1])) {
                  v1 = v;
                }
                if (v.getId() == Integer.parseInt(line[2])) {
                  v2 = v;
                }
              }
              EdgeLabel edgeLabel = LabelUtils.getEdgeLabel(line[3]);
              if (edgeLabel != null) {
                // 必须是四种标签之一
                Edge e = new Edge(v1, v2, edgeLabel);
                edges.add(e);
              }
            }
          }
          graph = new Graph(vertices, edges);
        }
      }
      target.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return graph;
  }
}
