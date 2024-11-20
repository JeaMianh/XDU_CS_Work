package sort;

import java.util.Arrays;
import java.util.Random;

public class Experiment {
    private static final int NUM_RUNS = 10;
    private static final int ARRAY_SIZE = 100000; // 测试数组大小

    public static void main(String[] args) {
        System.out.println("Sorting Algorithm Performance (Time in Microseconds, Space in KB)");

        // 测试并输出每种排序算法的性能结果
        testAndPrintResults("Insertion Sort", Sort::insertionSort);
        testAndPrintResults("Top-down Merge", Sort::topDownMergeSort);
        testAndPrintResults("Bottom-up Merge", Sort::bottomUpMergeSort);
        testAndPrintResults("Random Quicksort", Sort::randomQuicksort);
        testAndPrintResults("Dijkstra 3-way", Sort::dijkstra3WayQuicksort);
    }

    // 进行排序算法的时间和空间测试并输出表格结果
    private static void testAndPrintResults(String algorithmName, SortingAlgorithm algorithm) {
        long[] timeResults = new long[NUM_RUNS];
        long[] spaceResults = new long[NUM_RUNS];

        for (int i = 0; i < NUM_RUNS; i++) {
            int[] data = generateRandomArray(ARRAY_SIZE);

            // 测试空间
            spaceResults[i] = testSpaceUsage(() -> algorithm.sort(data.clone()));

            // 测试时间
            timeResults[i] = testTimeUsage(() -> algorithm.sort(data.clone()));
        }

        // 计算时间和空间的平均值
        double avgTime = Arrays.stream(timeResults).average().orElse(0.0);
        double avgSpace = Arrays.stream(spaceResults).average().orElse(0.0);

        // 输出表格格式的结果
        System.out.printf("%-15s", algorithmName);
        for (long time : timeResults) {
            System.out.printf("%-8d", time);
        }
        System.out.printf("Avg: %.2f μs\n", avgTime);

        System.out.printf("%-15s", "");
        for (long space : spaceResults) {
            System.out.printf("%-8d", space);
        }
        System.out.printf("Avg: %.2f KB\n\n", avgSpace);
    }

    // 随机数生成函数
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }

    // 时间测试函数
    private static long testTimeUsage(Runnable sortingTask) {
        long startTime = System.nanoTime();
        sortingTask.run();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000; // 转为微秒
    }

    // 空间测试函数
    private static long testSpaceUsage(Runnable sortingTask) {
        System.gc(); // 提前进行垃圾回收，减少干扰
        long memoryBefore = getCurrentMemoryUsage();
        sortingTask.run();
        long memoryAfter = getCurrentMemoryUsage();
        return (memoryAfter - memoryBefore) / 1024; // 转为 KB
    }

    // 获取当前内存使用情况
    private static long getCurrentMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    @FunctionalInterface
    private interface SortingAlgorithm {
        void sort(int[] array);
    }
}
