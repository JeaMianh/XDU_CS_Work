package maprouting;

import edu.princeton.cs.algs4.*;

import java.util.Scanner;

public class MapRouting {

    // 创建顶点和存储它们的坐标
    private static void initPoints(In in, Node[] nodes) {

        for (int i = 0; i < nodes.length; i++) {
            int id = in.readInt();
            int x = in.readInt();
            int y = in.readInt();
            nodes[i] = new Node(x, y);
        }
    }

    // 创建边并将其添加到图中
    private static void initGraph(In in, int numEdges, Node[] nodes, EdgeWeightedGraph graph) {

        for (int i = 0; i < numEdges; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = nodes[v].euDist(nodes[w]);  // 计算欧几里得距离作为权重
            graph.addEdge(new Edge(v, w, weight));
        }
    }

    public static void main(String[] args) {
        In in = new In("asset/usa.txt");

        int numVertices = in.readInt();  // 读取顶点数
        int numEdges = in.readInt();     // 读取边数

        EdgeWeightedGraph graph = new EdgeWeightedGraph(numVertices);
        Node[] nodes = new Node[numVertices];

        initPoints(in, nodes);
        initGraph(in, numEdges, nodes, graph);

        Scanner sc = new Scanner(System.in);
        System.out.print("输入起点：");
        int source = sc.nextInt();
        System.out.print("输入终点：");
        int destination = sc.nextInt();

        Route.route(graph, nodes, source, destination);
    }
}
