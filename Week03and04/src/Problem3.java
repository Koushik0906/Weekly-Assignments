import java.util.*;

public class Problem3 {

    static class Trade {
        String id;
        int volume;

        public Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return id + ":" + volume;
        }
    }

    public static void main(String[] args) {

        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        // 🔹 Merge Sort (Ascending)
        mergeSort(trades, 0, trades.length - 1);
        System.out.println("Merge Sort (ASC): " + Arrays.toString(trades));

        // 🔹 Quick Sort (Descending)
        quickSort(trades, 0, trades.length - 1);
        System.out.println("Quick Sort (DESC): " + Arrays.toString(trades));

        // 🔹 Merge two sorted lists
        Trade[] morning = {
                new Trade("m1", 100),
                new Trade("m2", 300)
        };

        Trade[] afternoon = {
                new Trade("a1", 200),
                new Trade("a2", 400)
        };

        Trade[] merged = mergeTwoLists(morning, afternoon);

        System.out.println("Merged List: " + Arrays.toString(merged));

        // 🔹 Total Volume
        System.out.println("Total Volume: " + totalVolume(merged));
    }

    // ================= MERGE SORT =================

    public static void mergeSort(Trade[] arr, int left, int right) {
        if (left >= right) return;

        int mid = (left + right) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }

    private static void merge(Trade[] arr, int left, int mid, int right) {

        List<Trade> temp = new ArrayList<>();

        int i = left, j = mid + 1;

        while (i <= mid && j <= right) {
            if (arr[i].volume <= arr[j].volume) { // stable
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

    // ================= QUICK SORT (DESC) =================

    public static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {

            int pivotIndex = partition(arr, low, high);

            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(Trade[] arr, int low, int high) {

        Trade pivot = arr[high]; // last element
        int i = low - 1;

        for (int j = low; j < high; j++) {

            // DESC order
            if (arr[j].volume > pivot.volume) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(Trade[] arr, int i, int j) {
        Trade temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // ================= MERGE TWO SORTED ARRAYS =================

    public static Trade[] mergeTwoLists(Trade[] a, Trade[] b) {

        Trade[] result = new Trade[a.length + b.length];

        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            if (a[i].volume <= b[j].volume) {
                result[k++] = a[i++];
            } else {
                result[k++] = b[j++];
            }
        }

        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];

        return result;
    }

    // ================= TOTAL VOLUME =================

    public static int totalVolume(Trade[] arr) {

        int sum = 0;

        for (Trade t : arr) {
            sum += t.volume;
        }

        return sum;
    }
}
