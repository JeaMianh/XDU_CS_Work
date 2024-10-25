package percolation;

import java.util.Random;
import java.util.Arrays;

public class PercolationStats {
    private int sidelength;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double[] experiments;

    public PercolationStats(int n, int T_Iteration, int choice) {
        if (n <= 0 || T_Iteration <= 0)
            throw new IllegalArgumentException("Illegal Argument Exception");
        sidelength = n;
        if (sidelength == 1) { // 变长仅为一时
            mean = 1;
            stddev = Double.NaN;
            confidenceLo = Double.NaN;
            confidenceHi = Double.NaN;
        } else {
            experiments = new double[T_Iteration];  // 记录每一次迭代的渗透阈值
            Random rand = new Random();
            for (int i = 0; i < T_Iteration; i++) {   // 进行T_Iteration次迭代
                Percolation percolation = new Percolation(n, choice);
                double count = 0;
                while (!percolation.percolates()) {   // 判断首尾是否已经连通
                    int row = rand.nextInt(n) + 1;    // 随机选择节点进行open
                    int col = rand.nextInt(n) + 1;
                    if (!percolation.isOpen(row, col)) {
                        percolation.open(row, col);
                        count++;                    // 记录open节点数量
                    }
                }
                experiments[i] = count / (n * n);  // 记录此次迭代空置概率p
            }
            mean = Arrays.stream(experiments).average().orElse(Double.NaN);  // 渗透阈值的样本均值
            stddev = calculateStdDev(experiments, mean);  // 渗透阈值的样本标准差
            confidenceLo = mean - (1.96 * stddev) / Math.sqrt(T_Iteration);  // 95％置信区间下限
            confidenceHi = mean + (1.96 * stddev) / Math.sqrt(T_Iteration);  // 95％置信区间上限
        }
    }

    // 计算标准差的辅助函数
    private double calculateStdDev(double[] values, double mean) {
        if (values.length == 0) return Double.NaN;
        double sum = 0;
        for (double v : values) {
            sum += (v - mean) * (v - mean);  // 求平方差之和
        }
        return Math.sqrt(sum / (values.length - 1));  // 样本标准差公式
    }

    // 返回样本均值
    public double mean() {
        return mean;
    }

    // 返回样本标准差
    public double stddev() {
        return stddev;
    }

    // 返回95％置信区间下限
    public double confidenceLo() {
        return confidenceLo;
    }

    // 返回95％置信区间上限
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = 100, T_Iteration = 200;               //n为网格数，T_Iteration为迭代次数
        //选择quickfind or weighted quickunion
        for (int choice = 1; choice <= 2; choice++) {
            System.out.println("--------------------------------------------------------");
            if (choice == 1) {
                System.out.println("The algorithm currently used is：Quick_Find");
            } else if (choice == 2) {
                System.out.println("The algorithm currently used is：Weighted Quick Union");
            }
            long start_time = System.nanoTime();      //记录开始时间
            PercolationStats percolationStats = new PercolationStats(n, T_Iteration, choice);
            long consumingtime = System.nanoTime() - start_time;  //计算总耗费时间
            System.out.println("Creating PercolationStats( " + n + " , " + T_Iteration + " )");
            System.out.println("mean:\t\t\t\t= " + percolationStats.mean());
            System.out.println("stddev:\t\t\t\t= " + percolationStats.stddev());
            System.out.println("confidence_Low:\t\t= " + percolationStats.confidenceLo());
            System.out.println("confidence_High:\t= " + percolationStats.confidenceHi());
            System.out.println("The cost of the time is " + consumingtime * 1.0 / 1000000 + "ms");
        }

    }
}

