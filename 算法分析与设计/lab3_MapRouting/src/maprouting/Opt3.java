package maprouting;

import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMultiwayMinPQ;

public class Opt3 extends DijkstraSP{
        private IndexMultiwayMinPQ pq;
    public Opt3(EdgeWeightedGraph graph, Node[] nodes, int source, int destination) {
        super(graph, nodes, source, destination);
        pq = new IndexMultiwayMinPQ<>(graph.V(), 2);
    }
}
