package algorithm;

import java.util.ArrayList;

/** solution 映射结果类 */
public class Solution {
  private ArrayList<MatchCouple> solution;

  public Solution() {}

  public Solution(ArrayList<MatchCouple> solution) {
    this.solution = solution;
  }

  public ArrayList<MatchCouple> getSolution() {
    return solution;
  }

  public void setSolution(ArrayList<MatchCouple> solution) {
    this.solution = solution;
  }

  @Override
  public String toString() {
    return "Solution{" + "solution=" + solution + '}';
  }
}
