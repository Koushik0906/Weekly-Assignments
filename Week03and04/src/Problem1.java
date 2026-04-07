import java.util.*;

public class Problem1 {

    static class Transaction {
        String id;
        double fee;
        String timestamp; // simple string for demo

        public Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return id + ":" + fee + "@" + timestamp;
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        System.out.println("Bubble Sort (by fee):");
        bubbleSort(transactions);

        System.out.println("\nInsertion Sort (fee + timestamp):");
        insertionSort(transactions);

        System.out.println("\nHigh Fee Outliers:");
        findOutliers(transactions);
    }

    // 🔹 Bubble Sort (Stable)
    public static void bubbleSort(List<Transaction> list) {

        int n = list.size();
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {

                if (list.get(j).fee > list.get(j + 1).fee) {

                    Collections.swap(list, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }

            if (!swapped) break; // optimization
        }

        System.out.println(list);
        System.out.println("Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (Stable)
    public static void insertionSort(List<Transaction> list) {

        for (int i = 1; i < list.size(); i++) {

            Transaction key = list.get(i);
            int j = i - 1;

            // Compare by fee, then timestamp
            while (j >= 0 &&
                    (list.get(j).fee > key.fee ||
                            (list.get(j).fee == key.fee &&
                                    list.get(j).timestamp.compareTo(key.timestamp) > 0))) {

                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }

        System.out.println(list);
    }

    // 🔹 Outlier Detection
    public static void findOutliers(List<Transaction> list) {

        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.println("High Fee: " + t);
            }
        }
    }
}
