package percolation;

public class Percolation {
    private boolean[] grid;
    private int N, lastIndex;
    private UnionFind unionFind;

    public Percolation(int N, int choice) { // create N-by-N grid, with all sites blocked
        this.N = N;
        lastIndex = N*N+1;
        if (choice == 1) unionFind = new QuickFind(N*N+2);
        else unionFind = new WeightedQuickUnion(N*N+2);
        grid = new boolean[N*N+2];
        grid[0] = true;
        grid[lastIndex] = true;
    }

    private int getIndex(int i, int j) {
        return N * (i - 1) + j;
    }

    public void open(int i, int j) { // open site (row i, column j) if it is not already
        if(i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException("Index Out Of Bounds Exception");
        int target = getIndex(i, j);
        if (grid[target]) return;  // 如果该位置已经是开放的，直接返回
        grid[target] = true;       // 将该位置标记为开放

        if (i == 1) unionFind.union(0, target); // 如果是第一行，则与虚拟顶部连接
        if (i == N) unionFind.union(target, lastIndex); // 如果是最后一行，则与虚拟底部连接

        int[] rowOffset = {-1, 1, 0, 0};  // 对应行的偏移量：上、下、左、右
        int[] colOffset = {0, 0, -1, 1};  // 对应列的偏移量：上、下、左、右

        for (int k = 0; k < 4; k++) {
            int newRow = i + rowOffset[k];   // 计算新的行坐标
            int newCol = j + colOffset[k];   // 计算新的列坐标
            if (newRow >= 1 && newRow <= N && newCol >= 1 && newCol <= N) {  // 如果新坐标在有效范围内
                int neighborIndex = getIndex(newRow, newCol);                // 获取相邻格子的索引
                if (grid[neighborIndex]) {                                   // 如果该相邻格子是开放的
                    unionFind.union(target, neighborIndex);                  // 连接当前格子和相邻格子
                }
            }
        }
    }

    public boolean isOpen(int i, int j) { // is site (row i, column j) open?
        if(i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException("Index Out Of Bounds Exception");
        return grid[getIndex(i, j)];
    }

    public boolean isFull(int i, int j) { // is site (row i, column j) full?
        if (i < 1 || i > N || j < 1 || j > N)
            throw new IndexOutOfBoundsException("Index Out Of Bounds Exception");

        int target = getIndex(i, j);  // 将 (i, j) 转换为一维索引

        // 检查当前格子是否与虚拟顶部节点连通
        return unionFind.connected(0, target);
    }


    public boolean percolates() { // does the system percolate?
        return unionFind.connected(0, lastIndex);
    }

}

