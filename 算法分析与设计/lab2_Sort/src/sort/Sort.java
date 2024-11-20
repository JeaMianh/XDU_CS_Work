package sort;

import java.util.Random;

public class Sort {

    // 插入排序
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    // 自顶向下归并排序
    public static void topDownMergeSort(int[] array) {
        int[] tempArray = new int[array.length];
        topDownMergeSort(array, tempArray, 0, array.length - 1);
    }

    private static void topDownMergeSort(int[] array, int[] tempArray, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        topDownMergeSort(array, tempArray, left, mid);
        topDownMergeSort(array, tempArray, mid + 1, right);
        merge(array, tempArray, left, mid, right);
    }

    private static void merge(int[] array, int[] tempArray, int left, int mid, int right) {
        System.arraycopy(array, left, tempArray, left, right - left + 1);
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) array[k] = tempArray[j++];
            else if (j > right) array[k] = tempArray[i++];
            else if (tempArray[i] <= tempArray[j]) array[k] = tempArray[i++];
            else array[k] = tempArray[j++];
        }
    }

    // 自底向上归并排序
    public static void bottomUpMergeSort(int[] array) {
        int n = array.length;
        int[] tempArray = new int[n];
        for (int width = 1; width < n; width *= 2) {
            for (int i = 0; i < n; i += 2 * width) {
                int mid = Math.min(i + width, n);
                int right = Math.min(i + 2 * width, n);
                merge(array, tempArray, i, mid - 1, right - 1);
            }
        }
    }

    // 随机快速排序
    public static void randomQuicksort(int[] array) {
        randomQuicksort(array, 0, array.length - 1);
    }

    private static void randomQuicksort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            randomQuicksort(array, low, pivotIndex - 1);
            randomQuicksort(array, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] array, int low, int high) {
        swap(array, low + new Random().nextInt(high - low + 1), high); // 随机选取枢纽并移动到最后
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) swap(array, ++i, j);
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // Dijkstra 3-路划分快速排序
    public static void dijkstra3WayQuicksort(int[] array) {
        dijkstra3WayQuicksort(array, 0, array.length - 1);
    }

    private static void dijkstra3WayQuicksort(int[] array, int low, int high) {
        if (low >= high) return;
        swap(array, low + new Random().nextInt(high - low + 1), low);
        int pivot = array[low];
        int lt = low, i = low + 1, gt = high;
        while (i <= gt) {
            if (array[i] < pivot) swap(array, lt++, i++);
            else if (array[i] > pivot) swap(array, i, gt--);
            else i++;
        }
        dijkstra3WayQuicksort(array, low, lt - 1);
        dijkstra3WayQuicksort(array, gt + 1, high);
    }
}
