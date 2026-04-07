import java.util.*;

public class Problem5 {

    public static void main(String[] args) {

        String[] logs = {"accB", "accA", "accB", "accC"};

        // 🔹 Linear Search (unsorted)
        System.out.println("Linear Search:");
        linearSearch(logs, "accB");

        // 🔹 Sort for Binary Search
        Arrays.sort(logs);
        System.out.println("\nSorted Logs: " + Arrays.toString(logs));

        // 🔹 Binary Search
        System.out.println("\nBinary Search:");
        binarySearch(logs, "accB");
    }

    // ================= LINEAR SEARCH =================

    public static void linearSearch(String[] arr, String target) {

        int first = -1, last = -1;
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;

            if (arr[i].equals(target)) {
                if (first == -1) first = i;
                last = i;
            }
        }

        System.out.println("First Occurrence: " + first);
        System.out.println("Last Occurrence: " + last);
        System.out.println("Comparisons: " + comparisons);
    }

    // ================= BINARY SEARCH =================

    public static void binarySearch(String[] arr, String target) {

        int comparisons = 0;

        int first = findFirst(arr, target);
        int last = findLast(arr, target);

        if (first == -1) {
            System.out.println("Not found");
            return;
        }

        int count = last - first + 1;

        System.out.println("Found at index: " + first);
        System.out.println("Count: " + count);
        System.out.println("Approx Comparisons: " + (int)(Math.log(arr.length) / Math.log(2)));
    }

    private static int findFirst(String[] arr, String target) {

        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                high = mid - 1; // go left
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    private static int findLast(String[] arr, String target) {

        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                low = mid + 1; // go right
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }
}
