package dev.bogdanjovanovic.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
  private final String data;
  private final List<Edge> edges;

  public Vertex(final String data) {
    this.data = data;
    this.edges = new ArrayList<>();
  }

  public void addEdge(final Vertex endVertex, final Integer weight) {
    this.edges.add(new Edge(this, endVertex, weight));
  }

  public void removeEdge(final Vertex endVertex) {
    this.edges.removeIf((edge) -> edge.getEndVertex().equals(endVertex));
  }

  public List<Edge> getEdges() {
    return this.edges;
  }

  public String getData() {
    return data;
  }

  public void printVertex(boolean showWeight) {
    final StringBuilder message = new StringBuilder();

    if (this.edges.isEmpty()) {
//      System.out.println(this.data + " ==>");
      return;
    }

    for (final Edge currentEdge : this.edges) {
      message.append(currentEdge.getStartVertex().getData()).append(" ==> ");

      if (showWeight) {
        message.append("(").append(currentEdge.getWeight()).append(") ==> ");
      }

      message.append(currentEdge.getEndVertex().getData());
    }

    System.out.println(message);
  }
}
