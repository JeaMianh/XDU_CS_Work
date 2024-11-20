package maprouting;

public class Node {
    private double x;  // x 坐标
    private double y;  // y 坐标

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // 计算当前点和另一个点之间的欧几里得距离
    public double euDist(Node that) {
        double deltaX = this.x - that.x;
        double deltaY = this.y - that.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    // 输出当前点的坐标
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

