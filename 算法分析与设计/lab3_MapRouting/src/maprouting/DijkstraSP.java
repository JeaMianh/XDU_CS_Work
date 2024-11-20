package maprouting;

import edu.princeton.cs.algs4.*;
import java.util.Arrays;

public class DijkstraSP {
    protected EdgeWeightedGraph graph;
    protected Node[] nodes;
    protected int source;
    protected int destination;
    protected double[] distTo;  // 从源节点到各个节点的最短距离
    protected Edge[] edgeTo;    // 从源节点到各个节点的最短路径中的最后一条边

    protected IndexMinPQ<Double> pq;  // 优先队列

    public DijkstraSP(EdgeWeightedGraph graph, Node[] nodes, int source, int destination) {
        this.graph = graph;
        this.nodes = nodes;
        this.source = source;
        this.destination = destination;
        distTo = new double[graph.V()];
        edgeTo = new Edge[graph.V()];
        pq = new IndexMinPQ<>(graph.V());

        // 初始化 distTo 数组，确保所有元素为正无穷
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        // 清空 edgeTo 数组
        Arrays.fill(edgeTo, null);
        distTo[source] = 0.0;

        // 清空 pq 中所有元素
        for (int i = 0; i < graph.V(); i++) {
            if (pq.contains(i)) {
                pq.delete(i);  // 删除 pq 中的元素
            }
        }

        // 将源节点插入优先队列
        pq.insert(source, distTo[source]);
    }

    // 松弛操作：检查是否能通过 v 节点更新 w 节点的距离
    protected void relax(Edge e, int v) {
        int w = e.other(v);

        double weight = distTo[v] + e.weight();

        if (distTo[w] > weight) {
            distTo[w] = weight; // 更新实际距离
            edgeTo[w] = e;
            if (pq.contains(w)) {
                pq.decreaseKey(w, distTo[w]); // 更新 w 的参考距离
            } else {
                pq.insert(w, distTo[w]); // 插入 PQ
            }
        }
    }

    // 路径回溯，返回源节点到目标节点 v 的最短路径
    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        int x = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[x]) {
            path.push(e);
            x = e.other(x);
        }
        return path;
    }

    // 返回源节点到目标节点 v 的最短距离
    public double distTo(int v) {
        return distTo[v];
    }

    // 判断是否存在从源节点到目标节点 v 的路径
    public boolean hasPathTo(int v) {

        while (!pq.isEmpty()) {
            int x = pq.delMin();  // 获取当前距离最小的节点

            for (Edge e : graph.adj(x)) {  // 遍历与节点 x 相邻的所有边
                relax(e, x);
            }
        }

        return distTo[v] < Double.POSITIVE_INFINITY;
    }

}

