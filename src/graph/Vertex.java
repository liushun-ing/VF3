package graph;

public class Vertex {
  private int id;
  private int inDegree;
  private int outDegree;
  private int dm;
  private double pf;
  private String label;

  public int getTotalDegree() {
    return this.inDegree + this.outDegree;
  }

  public Vertex() {}

  public Vertex(int id, String label) {
    this.id = id;
    this.inDegree = 0;
    this.outDegree = 0;
    this.dm = 0;
    this.pf = 0;
    this.label = label;
  }

  public Vertex(int id, int inDegree, int outDegree, int dm, double pf, String label) {
    this.id = id;
    this.inDegree = inDegree;
    this.outDegree = outDegree;
    this.dm = dm;
    this.pf = pf;
    this.label = label;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getInDegree() {
    return inDegree;
  }

  public void setInDegree(int inDegree) {
    this.inDegree = inDegree;
  }

  public int getOutDegree() {
    return outDegree;
  }

  public void setOutDegree(int outDegree) {
    this.outDegree = outDegree;
  }

  public double getPf() {
    return pf;
  }

  public void setPf(double pf) {
    this.pf = pf;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getDm() {
    return dm;
  }

  public void setDm(int dm) {
    this.dm = dm;
  }

  @Override
  public String toString() {
    return "Vertex{"
        + "id="
        + id
        + ", inDegree="
        + inDegree
        + ", outDegree="
        + outDegree
        + ", dm="
        + dm
        + ", pf="
        + pf
        + ", label='"
        + label
        + '\''
        + '}';
  }
}
