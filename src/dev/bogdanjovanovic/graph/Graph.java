package dev.bogdanjovanovic.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

  private final boolean isWeighted;
  private final boolean isDirected;
  private final List<Vertex> vertices;

  public Graph(final boolean isWeighted, final boolean isDirected) {
    this.isWeighted = isWeighted;
    this.isDirected = isDirected;
    this.vertices = new ArrayList<>();
  }

  public Vertex addVertex(final String data) {
    final Vertex vertex = new Vertex(data);
    this.vertices.add(vertex);

    return vertex;
  }

  public void removeVertex(final Vertex vertex) {
    this.vertices.remove(vertex);
  }

  public void addEdge(final Vertex vertex1, final Vertex vertex2, Integer weight) {
    if (!this.isWeighted) {
      weight = null;
    }
    vertex1.addEdge(vertex2, weight);
    if (!this.isDirected) {
      vertex2.addEdge(vertex1, weight);
    }
  }

  public void removeEdge(final Vertex vertex1, final Vertex vertex2) {
    vertex1.removeEdge(vertex2);
    if (!this.isDirected) {
      vertex2.removeEdge(vertex1);
    }
  }

  public void printVertices() {
    for (final Vertex vertex : this.vertices) {
      vertex.printVertex(this.isWeighted);
    }
  }

  public static void main(final String[] args) {
    final Graph busNetwork = new Graph(true, true);

    final Vertex noviBeogradStation = busNetwork.addVertex("Novi Beograd");
    final Vertex mostarStation = busNetwork.addVertex("Mostar");
    final Vertex banovoBrdoStation = busNetwork.addVertex("Banovo Brdo");

    // "edge" represents number of bus stops between each station
    busNetwork.addEdge(noviBeogradStation, mostarStation, 3);
    busNetwork.addEdge(mostarStation, banovoBrdoStation, 5);

    busNetwork.printVertices();
  }

}
