import java.util.*;

public class Problem09 {

    static class Transaction {
        int id;
        int amount;
        String merchant;
        String account;
        long timestamp; // milliseconds

        public Transaction(int id, int amount, String merchant, String account, long timestamp) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.account = account;
            this.timestamp = timestamp;
        }
    }

    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        long now = System.currentTimeMillis();

        transactions.add(new Transaction(1, 500, "StoreA", "acc1", now));
        transactions.add(new Transaction(2, 300, "StoreB", "acc2", now + 1000));
        transactions.add(new Transaction(3, 200, "StoreC", "acc3", now + 2000));
        transactions.add(new Transaction(4, 500, "StoreA", "acc4", now + 3000));

        System.out.println("Two Sum:");
        findTwoSum(transactions, 500);

        System.out.println("\nTwo Sum within 1 hour:");
        findTwoSumWithTimeWindow(transactions, 500, 3600_000);

        System.out.println("\nDuplicates:");
        detectDuplicates(transactions);

        System.out.println("\nK-Sum:");
        findKSum(transactions, 3, 1000);
    }

    // 1. Classic Two Sum
    public static void findTwoSum(List<Transaction> transactions, int target) {

        HashMap<Integer, Transaction> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                System.out.println("Pair: " +
                        map.get(complement).id + " & " + t.id);
            }

            map.put(t.amount, t);
        }
    }

    // 2. Two Sum with Time Window
    public static void findTwoSumWithTimeWindow(List<Transaction> transactions,
                                                int target,
                                                long windowMillis) {

        HashMap<Integer, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            int complement = target - t.amount;

            if (map.containsKey(complement)) {
                for (Transaction prev : map.get(complement)) {

                    if (Math.abs(t.timestamp - prev.timestamp) <= windowMillis) {
                        System.out.println("Pair (within window): " +
                                prev.id + " & " + t.id);
                    }
                }
            }

            map.computeIfAbsent(t.amount, k -> new ArrayList<>()).add(t);
        }
    }

    // 3. Duplicate Detection
    public static void detectDuplicates(List<Transaction> transactions) {

        HashMap<String, List<Transaction>> map = new HashMap<>();

        for (Transaction t : transactions) {

            String key = t.amount + "_" + t.merchant;

            map.computeIfAbsent(key, k -> new ArrayList<>()).add(t);
        }

        for (String key : map.keySet()) {

            List<Transaction> list = map.get(key);

            if (list.size() > 1) {

                Set<String> accounts = new HashSet<>();

                for (Transaction t : list) {
                    accounts.add(t.account);
                }

                if (accounts.size() > 1) {
                    System.out.println("Duplicate: " + key +
                            " Accounts: " + accounts);
                }
            }
        }
    }

    // 4. K-Sum (Backtracking)
    public static void findKSum(List<Transaction> transactions,
                                int k,
                                int target) {

        backtrack(transactions, k, target, 0,
                new ArrayList<>());
    }

    private static void backtrack(List<Transaction> transactions,
                                  int k,
                                  int target,
                                  int start,
                                  List<Transaction> path) {

        if (k == 0 && target == 0) {

            System.out.print("Combination: ");
            for (Transaction t : path) {
                System.out.print(t.id + " ");
            }
            System.out.println();
            return;
        }

        if (k == 0 || target < 0) return;

        for (int i = start; i < transactions.size(); i++) {

            Transaction t = transactions.get(i);

            path.add(t);
            backtrack(transactions, k - 1,
                    target - t.amount, i + 1, path);
            path.remove(path.size() - 1);
        }
    }
}
