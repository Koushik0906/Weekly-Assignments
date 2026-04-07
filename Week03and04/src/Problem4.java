import java.util.*;

public class Problem4 {

    static class Asset {
        String name;
        double returnRate;
        double volatility;

        public Asset(String name, double returnRate, double volatility) {
            this.name = name;
            this.returnRate = returnRate;
            this.volatility = volatility;
        }

        @Override
        public String toString() {
            return name + ":" + returnRate + "%";
        }
    }

    public static void main(String[] args) {

        Asset[] assets = {
                new Asset("AAPL", 12, 5),
                new Asset("TSLA", 8, 9),
                new Asset("GOOG", 15, 4)
        };

        // 🔹 Merge Sort (ASC, stable)
        mergeSort(assets, 0, assets.length - 1);
        System.out.println("Merge Sort (ASC): " + Arrays.toString(assets));

        // 🔹 Quick Sort (DESC + volatility ASC)
        quickSort(assets, 0, assets.length - 1);
        System.out.println("Quick Sort (DESC): " + Arrays.toString(assets));
    }

    // ================= MERGE SORT =================

    public static void mergeSort(Asset[] arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    private static void merge(Asset[] arr, int left, int mid, int right) {

        List<Asset> temp = new ArrayList<>();

        int i = left, j = mid + 1;

        while (i <= mid && j <= right) {
            if (arr[i].returnRate <= arr[j].returnRate) { // stable
                temp.add(arr[i++]);
            } else {
                temp.add(arr[j++]);
            }
        }

        while (i <= mid) temp.add(arr[i++]);
        while (j <= right) temp.add(arr[j++]);

        for (int k = 0; k < temp.size(); k++) {
            arr[left + k] = temp.get(k);
        }
    }

    // ================= QUICK SORT =================

    public static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {

            int pivotIndex = medianOfThree(arr, low, high);

            swap(arr, pivotIndex, high); // move pivot to end

            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Partition (DESC return, ASC volatility)
    private static int partition(Asset[] arr, int low, int high) {

        Asset pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {

            if (arr[j].returnRate > pivot.returnRate ||
                    (arr[j].returnRate == pivot.returnRate &&
                            arr[j].volatility < pivot.volatility)) {

                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    // ================= MEDIAN OF 3 =================

    private static int medianOfThree(Asset[] arr, int low, int high) {

        int mid = (low + high) / 2;

        double a = arr[low].returnRate;
        double b = arr[mid].returnRate;
        double c = arr[high].returnRate;

        if ((a > b && a < c) || (a < b && a > c)) return low;
        if ((b > a && b < c) || (b < a && b > c)) return mid;

        return high;
    }

    private static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
