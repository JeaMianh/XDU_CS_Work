package maprouting;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class Opt1 extends DijkstraSP{

    public Opt1(EdgeWeightedGraph graph, Node[] nodes, int source, int destination) {
        super(graph, nodes, source, destination);
    }

    @Override
    public boolean hasPathTo(int v) {

        while (!pq.isEmpty()) {
            int x = pq.delMin();  // 获取当前距离最小的节点

            // 优化 1
            if (x == v) {
                return true;
            }

            for (Edge e : graph.adj(x)) {  // 遍历与节点 x 相邻的所有边
                relax(e, x);
            }
        }

        return distTo[v] < Double.POSITIVE_INFINITY;
    }
}
