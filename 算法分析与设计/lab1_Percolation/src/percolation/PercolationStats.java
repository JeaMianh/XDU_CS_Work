package percolation;

import java.util.Arrays;
import java.util.Random;

public class PercolationStats {
    private final int sidelength;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double[] experiments;

    public PercolationStats(int n, int T_Iteration, int choice) {
        validateArguments(n, T_Iteration);  // 参数验证
        sidelength = n;

        if (sidelength == 1) {
            initializeSingleCellStats();  // 初始化单一格子的特殊情况
        } else {
            runExperiments(T_Iteration, choice);  // 执行实验
            calculateStatistics(T_Iteration);     // 计算统计数据
        }
    }

    // 参数验证函数
    private void validateArguments(int n, int T_Iteration) {
        if (n <= 0 || T_Iteration <= 0) {
            throw new IllegalArgumentException("Illegal Argument Exception");
        }
    }

    // 初始化单一格子的特殊情况
    private void initializeSingleCellStats() {
        mean = 1;
        stddev = Double.NaN;
        confidenceLo = Double.NaN;
        confidenceHi = Double.NaN;
    }

    // 执行渗透实验，记录每次的渗透阈值
    private void runExperiments(int T_Iteration, int choice) {
        experiments = new double[T_Iteration];
        Random rand = new Random();

        for (int i = 0; i < T_Iteration; i++) {
            Percolation percolation = new Percolation(sidelength, choice);
            double count = performSingleExperiment(percolation, rand);
            experiments[i] = count / (sidelength * sidelength);
        }
    }

    // 执行单次实验，返回打开的格子数量
    private double performSingleExperiment(Percolation percolation, Random rand) {
        double count = 0;
        while (!percolation.percolates()) {
            int row = rand.nextInt(sidelength) + 1;
            int col = rand.nextInt(sidelength) + 1;
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                count++;
            }
        }
        return count;
    }

    // 计算统计数据
    private void calculateStatistics(int T_Iteration) {
        mean = Arrays.stream(experiments).average().orElse(Double.NaN);
        stddev = calculateStdDev(experiments, mean);
        double marginOfError = (1.96 * stddev) / Math.sqrt(T_Iteration);
        confidenceLo = mean - marginOfError;
        confidenceHi = mean + marginOfError;
    }

    // 计算标准差的辅助函数
    private double calculateStdDev(double[] values, double mean) {
        if (values.length == 0) return Double.NaN;
        double sum = 0;
        for (double v : values) {
            sum += (v - mean) * (v - mean);
        }
        return Math.sqrt(sum / (values.length - 1));
    }

    // 打印统计信息
    public void printStats() {
        System.out.println("mean:\t\t\t\t= " + mean());
        System.out.println("stddev:\t\t\t\t= " + stddev());
        System.out.println("confidence_Low:\t\t= " + confidenceLo());
        System.out.println("confidence_High:\t= " + confidenceHi());
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
        int sideLength = 200, T_Iteration = 30;

        for (int choice = 0; choice <= 1; choice++) {
            System.out.println("--------------------------------------------------------");
            printAlgorithmChoice(choice);  // 打印算法选择信息

            long start_time = System.nanoTime();
            PercolationStats percolationStats = new PercolationStats(sideLength, T_Iteration, choice);
            long consumingTime = System.nanoTime() - start_time;

            System.out.println("Creating PercolationStats( " + sideLength + " , " + T_Iteration + " )");
            percolationStats.printStats();  // 打印统计结果
            System.out.println("The cost of the time is " + consumingTime / 1_000_000 + "ms");
        }
    }

    // 打印算法选择信息
    private static void printAlgorithmChoice(int choice) {
        if (choice == 0) {
            System.out.println("The current algorithm is：Quick_Find");
        } else if (choice == 1) {
            System.out.println("The current algorithm is：Weighted Quick Union");
        }
    }
}


