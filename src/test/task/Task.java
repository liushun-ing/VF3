package test.task;

import algorithm.BuildGraph;
import algorithm.MainEntry;
import algorithm.Solution;
import graph.Graph;

import java.util.ArrayList;

public class Task {
  public static void main(String[] args) {
    correctnessTest();
  }

  /**
   * 正确性测试
   */
  public static void correctnessTest() {
    MainEntry mainEntry = new MainEntry();
    ArrayList<Graph> targetGraph = new BuildGraph().buildTargetGraph(36, 40);
    for (Graph g : targetGraph) {
      ArrayList<ArrayList<Solution>> executeResult = mainEntry.executeById(g, 35);
      System.out.println(executeResult);
    }
  }
}
