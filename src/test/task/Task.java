package test.task;

import algorithm.BuildGraph;
import algorithm.MainEntry;
import algorithm.Solution;
import graph.Graph;

import java.util.ArrayList;

public class Task {
  public static void main(String[] args) {
    MainEntry mainEntry = new MainEntry();
    System.out.println(mainEntry.getPatternGraphs().get(0));
    ArrayList<Graph> targetGraph = new BuildGraph().buildTargetGraph(1, 40);
    for (Graph g : targetGraph) {
      ArrayList<ArrayList<Solution>> executeResult = mainEntry.executeById(g, 0);
      System.out.println(executeResult);
    }
  }
}
