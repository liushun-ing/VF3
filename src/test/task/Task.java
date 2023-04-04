package test.task;

import algorithm.BuildGraph;
import algorithm.MainEntry;
import algorithm.Solution;
import graph.Graph;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;

public class Task {
  public static void main(String[] args) {
//    correctnessTest();
//    boundaryTest();
    efficiencyTest();
  }

  /**
   * 正确性测试
   */
  public static void correctnessTest() {
    MainEntry mainEntry = new MainEntry();
    ArrayList<Graph> targetGraph = new BuildGraph().buildTargetGraph("36", "40");
    for (Graph g : targetGraph) {
      ArrayList<ArrayList<Solution>> executeResult = mainEntry.executeById(g, 35);
      System.out.println(executeResult);
    }
  }

  /**
   * 边界测试
   */
  public static void boundaryTest() {
    MainEntry mainEntry = new MainEntry();
    ArrayList<Graph> targetGraph = new BuildGraph().buildTargetGraph("boundary", "boundary_4");
    for (Graph g : targetGraph) {
      ArrayList<ArrayList<Solution>> executeResult = mainEntry.executeById(g, 0);
      System.out.println(executeResult);
    }
  }

  /**
   * 效率测试
   */
  public static void efficiencyTest() {
    MainEntry mainEntry = new MainEntry();
    ArrayList<Graph> targetGraph = new BuildGraph().buildTargetGraph("36", "40");
    long t = 0;
    for (Graph g : targetGraph) {
      long begin = new Date().getTime();
//      ArrayList<ArrayList<Solution>> executeResult = mainEntry.executeById(g, 35);
      ArrayList<ArrayList<Solution>> execute = mainEntry.execute(g);
      long end = new Date().getTime();
      t += (end - begin);
      System.out.println(end - begin);
    }
    System.out.println(t);
  }


}
