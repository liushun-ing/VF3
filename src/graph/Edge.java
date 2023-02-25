package graph;

import utils.EdgeLabel;

public class Edge {
  private Vertex startV;
  private Vertex endV;
  private EdgeLabel label;

  public Edge() {}

  public Edge(Vertex startV, Vertex endV, EdgeLabel label) {
    this.startV = startV;
    this.endV = endV;
    this.label = label;
  }

  public Vertex getStartV() {
    return startV;
  }

  public void setStartV(Vertex startV) {
    this.startV = startV;
  }

  public Vertex getEndV() {
    return endV;
  }

  public void setEndV(Vertex endV) {
    this.endV = endV;
  }

  public EdgeLabel getLabel() {
    return label;
  }

  public void setLabel(EdgeLabel label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "Edge{" + "startV=" + startV + ", endV=" + endV + ", label=" + label + '}';
  }
}
