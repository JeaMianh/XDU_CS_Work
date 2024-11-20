package maprouting;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;

public class Route {

    // 优化实例的工厂方法，使用枚举来选择优化类型
    private static DijkstraSP createSPInstance(EdgeWeightedGraph graph, Node[] nodes, int source, int destination, OptimizationType optimizationType) {
        switch (optimizationType) {
            case NO_OPT: return new DijkstraSP(graph, nodes, source, destination);
            case OPT_1: return new Opt1(graph, nodes, source, destination);
            case OPT_2: return new Opt2(graph, nodes, source, destination);
            case OPT_3: return new Opt3(graph, nodes, source, destination);
            case FULL_OPT: return new OptFull(graph, nodes, source, destination);
            default: throw new IllegalArgumentException("Invalid optimization type");
        }
    }

    // 用于计算路径和时间的公共方法
    private static void calculateRoute(DijkstraSP sp, int source, int destination) {
        long startTime = System.nanoTime();
        if (sp.hasPathTo(destination)) {
            double sum = 0.0;
            System.out.println(source + " 到 " + destination + " 的最短路径为:");
            for (Edge e : sp.pathTo(destination)) {
                System.out.println(e);
                sum += e.weight();
            }
            System.out.println("总距离为：" + sum);
        } else {
            System.out.println("路径不存在！");
        }
        long endTime = System.nanoTime();
        System.out.println("耗时：" + (endTime - startTime) / 1000000.0 + " ms\n");
    }

    // 主路由计算方法
    public static void route(EdgeWeightedGraph graph, Node[] nodes, int source, int destination) {
        for (OptimizationType optimizationType : OptimizationType.values()) {
            DijkstraSP sp = createSPInstance(graph, nodes, source, destination, optimizationType);
            System.out.println(optimizationType.getDescription());

            calculateRoute(sp, source, destination);
        }
    }
}

 enum OptimizationType {
    NO_OPT("无优化"),
    OPT_1("优化 1"),
    OPT_2("优化 2"),
    OPT_3("优化 3"),
    FULL_OPT("全优化");

    private final String description;

    // 构造方法，传入每个优化类型的描述
    OptimizationType(String description) {
        this.description = description;
    }

    // 获取优化类型的描述
    public String getDescription() {
        return description;
    }
}


