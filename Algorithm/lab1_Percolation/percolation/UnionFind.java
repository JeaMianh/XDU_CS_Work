package percolation;

public interface UnionFind {
    int find(int p);
    boolean connected(int p, int q);
    void union(int p, int q);
}
