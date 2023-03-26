package algorithm;

import graph.Graph;

import java.util.ArrayList;

/**
 * 算法的入口，new一个对象，然后执行execute方法
 */
public class MainEntry {
  private static ArrayList<Graph> patternGraphs = new ArrayList<>();

  public MainEntry() {
    // 只计算一次
    if (patternGraphs.size() > 0) {
      return;
    }
    patternGraphs = new BuildGraph().buildPatternGraphs();
  }

  /**
   * 传入targetGraph，执行VF3匹配算法
   *
   * @param targetGraph 目标图
   * @return ArrayList<ArrayList < Solution>>匹配到的所有结果
   */
  public ArrayList<ArrayList<Solution>> execute(Graph targetGraph) {
    ArrayList<ArrayList<Solution>> solutionsList = new ArrayList<>();
    for (Graph patternGraph : patternGraphs) {
      solutionsList.add(GraphMatch.VF3(patternGraph, targetGraph));
      // 匹配之后要重置模式图，方便下一次匹配
      patternGraph.resetGraph();
    }
    return solutionsList;
  }

  /**
   * 传入targetGraph，执行VF3匹配算法
   *
   * @param targetGraph 目标图
   * @return ArrayList<ArrayList < Solution>>匹配到的所有结果
   */
  public ArrayList<ArrayList<Solution>> executeById(Graph targetGraph, int index) {
    ArrayList<ArrayList<Solution>> solutionsList = new ArrayList<>();
    Graph patternGraph = patternGraphs.get(index);
    solutionsList.add(GraphMatch.VF3(patternGraph, targetGraph));
    // 匹配之后要重置模式图，方便下一次匹配
    patternGraph.resetGraph();
    return solutionsList;
  }

  public ArrayList<Graph> getPatternGraphs() {
    return patternGraphs;
  }
}
