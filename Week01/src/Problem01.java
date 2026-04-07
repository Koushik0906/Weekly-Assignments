import java.util.*;

public class Problem01 {

    // username -> userId (simulating existing users)
    private static HashMap<String, Integer> userDatabase = new HashMap<>();

    // username -> attempt count
    private static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    public static void main(String[] args) {

        // Preload some users
        userDatabase.put("john_doe", 1);
        userDatabase.put("admin", 2);
        userDatabase.put("user123", 3);

        // Test cases
        System.out.println("Availability:");
        System.out.println("john_doe → " + checkAvailability("john_doe"));
        System.out.println("jane_smith → " + checkAvailability("jane_smith"));

        System.out.println("\nSuggestions for 'john_doe':");
        System.out.println(suggestAlternatives("john_doe"));

        // Simulate multiple attempts
        checkAvailability("admin");
        checkAvailability("admin");
        checkAvailability("admin");

        System.out.println("\nMost Attempted Username:");
        System.out.println(getMostAttempted());
    }

    // O(1) availability check
    public static boolean checkAvailability(String username) {

        // update attempt frequency
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        return !userDatabase.containsKey(username);
    }

    // Suggest alternatives if username is taken
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        // only suggest if already taken
        if (!userDatabase.containsKey(username)) {
            suggestions.add(username); // already available
            return suggestions;
        }

        // generate suggestions
        suggestions.add(username + "1");
        suggestions.add(username + "2");
        suggestions.add(username.replace("_", "."));
        suggestions.add(username + "_official");

        return suggestions;
    }

    // Get most attempted username
    public static String getMostAttempted() {

        String mostAttempted = null;
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                mostAttempted = entry.getKey();
            }
        }

        return mostAttempted + " (" + max + " attempts)";
    }
}
