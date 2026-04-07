import java.util.*;

public class Problem02 {

    // productId -> stock count
    private static HashMap<String, Integer> inventory = new HashMap<>();

    // productId -> waiting list (FIFO)
    private static HashMap<String, Queue<Integer>> waitingList = new HashMap<>();

    public static void main(String[] args) {

        // Initialize product with 5 units (use small number for demo)
        inventory.put("IPHONE15_256GB", 5);
        waitingList.put("IPHONE15_256GB", new LinkedList<>());

        System.out.println(checkStock("IPHONE15_256GB"));

        // Simulate purchases
        for (int i = 1; i <= 8; i++) {
            System.out.println(purchaseItem("IPHONE15_256GB", i));
        }

        // Show waiting list
        System.out.println("\nWaiting List:");
        System.out.println(waitingList.get("IPHONE15_256GB"));
    }

    // O(1) stock check
    public static String checkStock(String productId) {
        int stock = inventory.getOrDefault(productId, 0);
        return productId + " → " + stock + " units available";
    }

    // Thread-safe purchase method
    public static synchronized String purchaseItem(String productId, int userId) {

        int stock = inventory.getOrDefault(productId, 0);

        // If stock available
        if (stock > 0) {
            inventory.put(productId, stock - 1);
            return "User " + userId + " → Purchase SUCCESS, remaining: " + (stock - 1);
        }

        // If out of stock → add to waiting list
        Queue<Integer> queue = waitingList.get(productId);
        queue.add(userId);

        return "User " + userId + " → Added to waiting list, position #" + queue.size();
    }
}
