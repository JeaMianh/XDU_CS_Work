package maprouting;

import edu.princeton.cs.algs4.*;

public class OptFull extends DijkstraSP {
//    protected IndexMultiwayMinPQ<Double> pq;  // 多路优先队列

    public OptFull(EdgeWeightedGraph graph, Node[] nodes, int source, int destination) {
        super(graph, nodes, source, destination);
        // 初始化新的优先队列，使用多路队列
//        pq = new IndexMultiwayMinPQ<>(graph.V(), 2);  // 2 为分支因子，可以根据需要调整
//        distTo[source] = 0.0;
//        pq.insert(source, distTo[source]);

    }

    // 重写松弛操作（启发式距离）
    @Override
    protected void relax(Edge e, int v) {
        int w = e.other(v);

        // 启发式距离优化：考虑到目标节点的启发式距离
        double weight = distTo[v] + e.weight();
        double heuristic = nodes[w].euDist(nodes[destination]) - nodes[v].euDist(nodes[destination]);
        double distance = weight + heuristic;

        // 更新距离
        if (distTo[w] > distance) {
            distTo[w] = distance;
            edgeTo[w] = e;
            // 更新优先队列中的元素
            if (pq.contains(w)) {
                pq.decreaseKey(w, distance);
            } else {
                pq.insert(w, distance);
            }
        }
    }


    @Override
    public boolean hasPathTo(int v) {

        while (!pq.isEmpty()) {
            int x = pq.delMin();  // 获取当前距离最小的节点

            // 优化 1
//            if (x == v) {
//                return true;
//            }

            for (Edge e : graph.adj(x)) {  // 遍历与节点 x 相邻的所有边
                relax(e, x);
            }
        }

        return distTo[v] < Double.POSITIVE_INFINITY;
    }
}


