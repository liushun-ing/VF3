package test.task;

import algorithm.BuildGraph;
import algorithm.MainEntry;
import algorithm.Solution;
import graph.Graph;

import java.util.ArrayList;

public class Task {
  public static void main(String[] args) {
    MainEntry mainEntry = new MainEntry();
    System.out.println(mainEntry.getPatternGraphs().get(1));
    ArrayList<Graph> targetGraph = new BuildGraph().buildTargetGraph(3, 40);
    for (Graph g : targetGraph) {
      ArrayList<ArrayList<Solution>> executeResult = mainEntry.executeById(g, 2);
      System.out.println(executeResult);
    }
  }
}
