import java.util.*;

public class Problem6 {

    public static void main(String[] args) {

        int[] risks = {10, 25, 50, 100};

        // 🔹 Linear Search (unsorted case simulation)
        System.out.println("Linear Search:");
        linearSearch(risks, 30);

        // 🔹 Binary Search Variants (sorted)
        System.out.println("\nBinary Search:");
        int target = 30;

        int floor = findFloor(risks, target);
        int ceil = findCeiling(risks, target);
        int insertPos = findInsertionPoint(risks, target);

        System.out.println("Floor(" + target + "): " + floor);
        System.out.println("Ceiling(" + target + "): " + ceil);
        System.out.println("Insertion Index: " + insertPos);
    }

    // ================= LINEAR SEARCH =================

    public static void linearSearch(int[] arr, int target) {

        int comparisons = 0;
        boolean found = false;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;

            if (arr[i] == target) {
                found = true;
                System.out.println("Found at index: " + i);
                break;
            }
        }

        if (!found) {
            System.out.println("Not found");
        }

        System.out.println("Comparisons: " + comparisons);
    }

    // ================= FLOOR =================
    // Largest element ≤ target

    public static int findFloor(int[] arr, int target) {

        int low = 0, high = arr.length - 1;
        int floor = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            }

            if (arr[mid] < target) {
                floor = arr[mid]; // possible answer
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return floor;
    }

    // ================= CEILING =================
    // Smallest element ≥ target

    public static int findCeiling(int[] arr, int target) {

        int low = 0, high = arr.length - 1;
        int ceil = -1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return arr[mid];
            }

            if (arr[mid] > target) {
                ceil = arr[mid]; // possible answer
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ceil;
    }

    // ================= INSERTION POINT =================
    // First index where element ≥ target (lower_bound)

    public static int findInsertionPoint(int[] arr, int target) {

        int low = 0, high = arr.length;

        while (low < high) {

            int mid = (low + high) / 2;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }
}
