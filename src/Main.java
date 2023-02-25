import algorithm.BuildGraph;
import algorithm.MainEntry;
import algorithm.Solution;
import graph.Graph;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    MainEntry mainEntry = new MainEntry();
    System.out.println(mainEntry.getPatternGraphs().size());
    Graph targetGraph = new BuildGraph().buildTargetGraph();
    System.out.println(targetGraph);
    ArrayList<ArrayList<Solution>> executeResult = mainEntry.execute(targetGraph);
    System.out.println(executeResult);
  }
}
