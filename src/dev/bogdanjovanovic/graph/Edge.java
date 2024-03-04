package dev.bogdanjovanovic.graph;

public class Edge {

  private final Vertex startVertex;
  private final Vertex endVertex;
  private final Integer weight;

  public Edge(final Vertex startVertex, final Vertex endVertex, final Integer weight) {
    this.startVertex = startVertex;
    this.endVertex = endVertex;
    this.weight = weight;
  }

  public Vertex getStartVertex() {
    return startVertex;
  }

  public Vertex getEndVertex() {
    return endVertex;
  }

  public Integer getWeight() {
    return weight;
  }
}
