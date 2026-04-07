import java.util.*;

public class Problem05 {

    // pageUrl → total visits
    private static HashMap<String, Integer> pageViews = new HashMap<>();

    // pageUrl → unique users
    private static HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();

    // source → count
    private static HashMap<String, Integer> trafficSources = new HashMap<>();

    public static void main(String[] args) {

        // Simulate incoming events
        processEvent("/news/breaking", "user1", "google");
        processEvent("/news/breaking", "user2", "facebook");
        processEvent("/sports/match", "user3", "direct");
        processEvent("/news/breaking", "user1", "google");
        processEvent("/sports/match", "user4", "google");
        processEvent("/tech/ai", "user5", "direct");

        // Display dashboard
        getDashboard();
    }

    // Process event in O(1)
    public static void processEvent(String url, String userId, String source) {

        // Update page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // Update unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // Update traffic source
        trafficSources.put(source,
                trafficSources.getOrDefault(source, 0) + 1);
    }

    // Generate dashboard
    public static void getDashboard() {

        System.out.println("\n===== DASHBOARD =====");

        // Top 10 pages (using PriorityQueue)
        PriorityQueue<Map.Entry<String, Integer>> pq =
                new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());

        pq.addAll(pageViews.entrySet());

        System.out.println("\nTop Pages:");

        int rank = 1;
        while (!pq.isEmpty() && rank <= 10) {

            Map.Entry<String, Integer> entry = pq.poll();

            String url = entry.getKey();
            int views = entry.getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(rank + ". " + url +
                    " - " + views + " views (" + unique + " unique)");

            rank++;
        }

        // Traffic source distribution
        System.out.println("\nTraffic Sources:");

        int total = trafficSources.values().stream().mapToInt(i -> i).sum();

        for (Map.Entry<String, Integer> entry : trafficSources.entrySet()) {

            double percent = (entry.getValue() * 100.0) / total;

            System.out.println(entry.getKey() + ": " +
                    String.format("%.2f", percent) + "%");
        }
    }
}
