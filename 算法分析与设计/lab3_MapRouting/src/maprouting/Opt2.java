package maprouting;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class Opt2 extends DijkstraSP{

    public Opt2(EdgeWeightedGraph graph, Node[] nodes, int source, int destination) {
        super(graph, nodes, source, destination);
    }

    @Override
    protected void relax(Edge e, int v) {
        int w = e.other(v);

        double weight = distTo[v] + e.weight();
        //优化2进行优化
        double distance = weight + nodes[w].euDist(nodes[destination]) - nodes[v].euDist(nodes[destination]);

        if (distTo[w] > distance) {
            distTo[w] = distance;
            edgeTo[w] = e;
            if (pq.contains(w)) {
                pq.decreaseKey(w, distance); // 更新 w 的参考距离
            } else {
                pq.insert(w, distance); // 插入 PQ
            }
        }

    }
}
